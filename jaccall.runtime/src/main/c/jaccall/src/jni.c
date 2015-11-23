#include <jni.h>
#include <stdlib.h>
#include <stdint.h>
#include <dlfcn.h>
#include <ffi.h>
#include <assert.h>

#include "com_github_zubnix_jaccall_JNI.h"

struct jni_call_data {
    ffi_cif *cif;
    void *symaddr;
};

JNIEXPORT
jobject
JNICALL Java_com_github_zubnix_jaccall_JNI_wrap(JNIEnv *env, jclass clazz, jlong address, jlong size) {
    return (*env)->NewDirectByteBuffer(env, (void *) (intptr_t) address, (size_t) size);
}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_unwrap(JNIEnv *env, jclass clazz, jobject byteBuffer) {
    return (jlong) (intptr_t) (*env)->GetDirectBufferAddress(env, byteBuffer);
}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_malloc(JNIEnv *env, jclass clazz, jint size) {
    return (jlong) (intptr_t) malloc((size_t) size);
}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_calloc(JNIEnv *env, jclass clazz, jint nmemb, jint size) {
    return (jlong) (intptr_t) calloc((size_t) nmemb, (size_t) size);
}

JNIEXPORT
void
JNICALL Java_com_github_zubnix_jaccall_JNI_free(JNIEnv *env, jclass clazz, jlong address) {
    free((void *) (intptr_t) address);
}

JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNI_sizeOfPointer(JNIEnv *env, jclass clazz) {
    return (jint) sizeof(void *);
}

JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNI_sizeOfCLong(JNIEnv *env, jclass clazz) {
    return (jint) sizeof(long);
}


static inline
void *
find_libaddr(JNIEnv *env, jstring library) {
    const char *lib_str = (*env)->GetStringUTFChars(env, library, 0);
    void *lib_addr = dlopen(lib_str, RTLD_NOW | RTLD_GLOBAL);
    if (!lib_addr) {
        fprintf(stderr, "dlopen error: %s\n", dlerror());
        exit(1);
    }
    (*env)->ReleaseStringUTFChars(env, library, lib_str);

    return lib_addr;
}

static inline
void prep_jni_cif(ffi_cif *jni_cif, const char *jni_sig, int arg_size) {
    //prepare jni ffi_cif by parsing jni signature
    //compensate for jnienv & jobject/jclass arguments
    ffi_type **args = malloc((sizeof(ffi_type *) * (arg_size + 2)));
    args[0] = &ffi_type_pointer;
    args[1] = &ffi_type_pointer;

    ffi_type *return_type = NULL;
    int parameter = 0;
    ffi_type *type = NULL;

    int i = 2;
    while (*jni_sig) {
        char jni_sig_char = *jni_sig;
        jni_sig++;

        switch (jni_sig_char) {
            case 'B' :
                type = &ffi_type_sint8;
                break;
            case 'S' :
                type = &ffi_type_sint16;
                break;
            case 'I' :
                type = &ffi_type_sint32;
                break;
            case 'J' :
                type = &ffi_type_sint64;
                break;
            case 'F' :
                type = &ffi_type_float;
                break;
            case 'D' :
                type = &ffi_type_double;
                break;
            case 'V' :
                assert(!parameter);
                type = &ffi_type_void;
                break;
            case '(' :
                parameter++;
                continue;
            case ')' :
                parameter--;
                continue;
            default:
                fprintf(stderr, "unsupported JNI argument: %c\n", jni_sig_char);
                exit(1);
        }

        assert(type);

        if (parameter) {
            args[i] = type;
            i++;
        } else /* return type*/ {
            return_type = type;
        }
    }

    assert(return_type);

    ffi_status status = ffi_prep_cif(jni_cif, FFI_DEFAULT_ABI, (unsigned int) arg_size + 2, return_type, args);
    if (status != FFI_OK) {
        fprintf(stderr, "ffi_prep_cif failed: %d\n", status);
        exit(1);
    }
}

static
void jni_call_handler(ffi_cif *cif, void *ret, void **jargs, void *user_data) {

    struct jni_call_data *call_data = user_data;

    ffi_type **arg_types = call_data->cif->arg_types;
    unsigned int nargs = call_data->cif->nargs;
    ffi_type *rtype = call_data->cif->rtype;

    void** args = nargs ? &jargs[2] : NULL;

    int i = 0;
    for (; i < nargs; i++) {
        if (arg_types[i]->type == FFI_TYPE_STRUCT) {
            //struct by value
            args[i] = *((void **) args[i]);
        }
    }

    if (rtype->type == FFI_TYPE_STRUCT) {
        //struct by value
        void *rval = malloc(rtype->size);
        ffi_call(call_data->cif, FFI_FN(call_data->symaddr), rval, args);
        *((void **) ret) = rval;
    } else {
        ffi_call(call_data->cif, FFI_FN(call_data->symaddr), ret, args);
    }
}

static
inline
void create_closure(const char *symstr,
                    void *symaddr,
                    const char *jni_sig,
                    jbyte argSize,
                    ffi_cif *cif,
                    JNINativeMethod *jniMethods_i) {
    void *jni_func;
    ffi_closure *closure = ffi_closure_alloc(sizeof(ffi_closure), &jni_func);
    if (closure) {

        ffi_cif *jni_cif = malloc(sizeof(ffi_cif));

        prep_jni_cif(jni_cif, jni_sig, argSize);

        struct jni_call_data *call_data = malloc(sizeof(struct jni_call_data));
        call_data->cif = cif;
        call_data->symaddr = symaddr;

        ffi_status status = ffi_prep_closure_loc(closure, jni_cif, &jni_call_handler, call_data, jni_func);
        if (status == FFI_OK) {
            jniMethods_i->name = (char *) symstr;
            jniMethods_i->signature = (char *) jni_sig;
            jniMethods_i->fnPtr = jni_func;

        } else {
            fprintf(stderr,
                    "ffi_prep_closure_loc failed: %d\n", status);
            exit(1);
        }
    }
    else {
        fprintf(stderr,
                "ffi_closure_alloc failed: %s\n", symstr);
        exit(1);
    }
}

JNIEXPORT void JNICALL Java_com_github_zubnix_jaccall_JNI_link(JNIEnv *env, jclass clazz, jstring library,
                                                               jclass headerClazz, jobjectArray symbols,
                                                               jbyteArray argumentSizes,
                                                               jobjectArray jniSignatures,
                                                               jlongArray ffiCallInterfaces) {

//get handle to shared lib
    void *libaddr = find_libaddr(env, library);
    jbyte *argSizes = (*env)->GetByteArrayElements(env, argumentSizes, 0);
    jlong *ffi_cifs = (*env)->GetLongArrayElements(env, ffiCallInterfaces, 0);
    int symbolsCount = (*env)->GetArrayLength(env, symbols);
    JNINativeMethod *jniMethods = malloc(sizeof(JNINativeMethod) * symbolsCount);

    int i = 0;
    for (; i < symbolsCount; i++) {

        jstring symbol = (jstring) (*env)->GetObjectArrayElement(env, symbols, i);
        const char *symstr = (*env)->GetStringUTFChars(env, symbol, 0);

        void *symaddr = dlsym(libaddr, symstr);
        char *err = dlerror();
        if (err) {
            fprintf(stderr,
                    "dlsym failed: %s\n", err);
            exit(1);
        }

        jstring jniSignature = (jstring) (*env)->GetObjectArrayElement(env, jniSignatures, i);
        const char *jni_sig = (*env)->GetStringUTFChars(env, jniSignature, 0);
        jbyte argSize = argSizes[i];
        ffi_cif *cif = (ffi_cif *) (intptr_t) ffi_cifs[i];
        JNINativeMethod *jniMethods_i = &jniMethods[i];

        create_closure(symstr, symaddr, jni_sig, argSize, cif, jniMethods_i);
    }


    (*env)->RegisterNatives(env, headerClazz, jniMethods, symbolsCount);
}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1void(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_void;
}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1sint8(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_sint8;
}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1uint8(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_uint8;
}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1sint16(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_sint16;

}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1uint16(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_uint16;

}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1sint32(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_sint32;

}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1uint32(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_uint32;

}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1slong(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_slong;

}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1ulong(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_ulong;

}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1sint64(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_sint64;

}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1uint64(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_uint64;

}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1float(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_float;

}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1double(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_double;

}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1pointer(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_pointer;
}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1struct(JNIEnv *env, jclass clazz,
                                                             jlongArray ffi_types) {
    ffi_type *struct_description = malloc(sizeof(ffi_type));
    jlong *struct_ctypes = (*env)->GetLongArrayElements(env, ffi_types, 0);
    int nro_fields = (*env)->GetArrayLength(env, ffi_types);
    ffi_type **struct_fields = malloc(sizeof(ffi_type *) * (nro_fields + 1));
    struct_fields[nro_fields] = NULL;

    int i = 0;
    for (; i < nro_fields; i++) {
        struct_fields[i] = (ffi_type *) (intptr_t) struct_ctypes[i];
    }

    struct_description->type = FFI_TYPE_STRUCT;
    struct_description->size = 0;
    struct_description->alignment = 0;
    struct_description->elements = struct_fields;

    ffi_cif cif;
    if (ffi_prep_cif(&cif, FFI_DEFAULT_ABI, 0, struct_description, NULL) != FFI_OK) {
        fprintf(stderr,
                "ffi_type struct failed.\n");
        exit(1);
    }

    return (jlong) (intptr_t) struct_description;
}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1union(JNIEnv *env, jclass clazz,
                                                            jlongArray ffi_types) {
    ffi_type *struct_description = malloc(sizeof(ffi_type));
    jlong *struct_ctypes = (*env)->GetLongArrayElements(env, ffi_types, 0);
    int nro_fields = (*env)->GetArrayLength(env, ffi_types);

    ffi_type **union_fields = malloc(sizeof(ffi_type *) * (2));
    union_fields[1] = NULL;

    struct_description->type = FFI_TYPE_STRUCT;
    struct_description->size = 0;
    struct_description->alignment = 0;
    struct_description->elements = union_fields;

    int i = 0;
    for (; i < nro_fields; ++i) {

        ffi_type *union_field = (ffi_type *) (intptr_t) struct_ctypes[i];

        ffi_cif cif;
        if (ffi_prep_cif(&cif, FFI_DEFAULT_ABI, 0, union_field, NULL) == FFI_OK) {
            if (union_field->size > struct_description->size) {
                struct_description->size = union_field->size;
                struct_description->elements[0] = union_field;
            }
            if (union_field->alignment > struct_description->alignment) {
                struct_description->alignment = union_field->alignment;
            }
        }
    }

    struct_description->size = 0;
    struct_description->alignment = 0;

    return (jlong) (intptr_t) struct_description;
}

JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1type_1struct_1size(JNIEnv *env, jclass clazz,
                                                                   jlong ffi_struct_type) {
    ffi_type *struct_description = (ffi_type *) (intptr_t) ffi_struct_type;
    return (jint) struct_description->size;
}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1callInterface(JNIEnv *env, jclass clazz,
                                                              jlong ffi_return_type,
                                                              jlongArray ffi_types) {
    jlong *struct_ctypes = (*env)->GetLongArrayElements(env, ffi_types, 0);
    int nro_args = (*env)->GetArrayLength(env, ffi_types);

    ffi_type **args = malloc(sizeof(ffi_type *) * nro_args);

    int i = 0;
    for (; i < nro_args; i++) {
        args[i] = (ffi_type *) (intptr_t) struct_ctypes[i];
    }

    ffi_cif *cif = malloc(sizeof(ffi_cif));
    if (ffi_prep_cif(cif, FFI_DEFAULT_ABI, (unsigned int) nro_args, (ffi_type *) (intptr_t) ffi_return_type, args) !=
        FFI_OK) {
        fprintf(stderr,
                "ffi_prep_cif failed.\n");
        exit(1);
    }

    return (jlong) (intptr_t) cif;
}

JNIEXPORT
void
JNICALL Java_com_github_zubnix_jaccall_JNI_linkFuncPtr (JNIEnv *env, jclass clazz, jclass wrapper, jstring symbol,
                                                        jint arg_size, jstring jni_sig, jlong cif){
}

static
void callback_handler(ffi_cif *cif, void *ret, void **args, void *user_data) {

}

JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1closure(JNIEnv *env, jclass clazz, jlong cif, jobject object) {

}

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    ffi_closure_free
 * Signature: (J)V
 */
JNIEXPORT
void
JNICALL Java_com_github_zubnix_jaccall_JNI_ffi_1closure_1free(JNIEnv *env, jclass clazz, jlong closure) {
    ffi_closure_free((void*)(intptr_t)closure);
}