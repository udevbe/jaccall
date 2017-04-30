#include <jni.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include <dlfcn.h>
#include <ffi.h>
#include <assert.h>

#include "org_freedesktop_jaccall_JNI.h"

static JavaVM *jvm;

#define NO_BYVAL 0x00
#define ARG_BYVAL 0x01
#define RET_BYVAL 0x02
#define BYVAL (ARG_BYVAL|RET_BYVAL)

//TODO we can write out specific call back handlers for all cases instead of checking at runtime.
struct jni_call_data {
    /* cif or one of ffi_type_... */
    ffi_cif *cif;
    /* address of the symbol */
    void *symaddr;
};

struct java_call_data {
    jmethodID mid;
    jobject object;
};

struct char_alignment {
    char a;
    char b;
};

struct short_alignment {
    char a;
    short b;
};

struct int_alignment {
    char a;
    int b;
};

struct long_alignment {
    char a;
    long b;
};

struct long_long_alignment {
    char a;
    long long b;
};

struct pointer_alignment {
    char a;
    void* b;
};

struct float_alignment {
    char a;
    float b;
};

struct double_alignment {
    char a;
    double b;
};

size_t offset_of_char = offsetof(struct char_alignment, b);
size_t offset_of_short = offsetof(struct short_alignment, b);
size_t offset_of_int = offsetof(struct int_alignment, b);
size_t offset_of_long = offsetof(struct long_alignment, b);
size_t offset_of_long_long = offsetof(struct long_long_alignment, b);
size_t offset_of_pointer = offsetof(struct pointer_alignment, b);
size_t offset_of_float = offsetof(struct float_alignment, b);
size_t offset_of_double = offsetof(struct double_alignment, b);

void throwError( JNIEnv *env, char *message, ...)
{
    jclass exClass;
    char *className = "java/lang/Error";

    exClass = (*env)->FindClass( env, className);

    va_list args;
    va_start (args, message);

    char* string;
    vasprintf(&string, message, args);

    (*env)->ThrowNew( env, exClass, string );

    va_end(args);
    free(string);
}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_NewGlobalRef(JNIEnv *env, jclass clazz, jobject object){
    return (jlong) (intptr_t) (*env)->NewGlobalRef(env, object);
}

/*
 * Class:     org_freedesktop_jaccall_JNI
 * Method:    toObject
 * Signature: (J)Lcom/github/zubnix/jaccall/JObject;
 */
JNIEXPORT
jobject JNICALL Java_org_freedesktop_jaccall_JNI_toObject(JNIEnv *env, jclass clazz, jlong jobject_address){
    return (jobject)(intptr_t)jobject_address;
}

JNIEXPORT
void
JNICALL Java_org_freedesktop_jaccall_JNI_DeleteGlobalRef(JNIEnv *env, jclass clazz, jlong object){
    (*env)->DeleteGlobalRef(env, (jobject)(intptr_t)object);
}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_GetMethodID(JNIEnv *env, jclass clazz, jclass target,
                                                        jstring jniMethodName, jstring jniSignature){
    const char *methodName = (*env)->GetStringUTFChars(env, jniMethodName, 0);
    const char *signature = (*env)->GetStringUTFChars(env, jniSignature, 0);

    jmethodID mid = (*env)->GetMethodID(env, target, methodName, signature);
    if(mid == NULL) {
        throwError(env, "Could not find method id for %s with signature %s",methodName, signature);
        return 0;
    }

    (*env)->ReleaseStringUTFChars(env, jniMethodName, methodName);
    (*env)->ReleaseStringUTFChars(env, jniSignature, signature);

    return (jlong)(intptr_t) mid;
}


static inline
void *
find_libaddr(JNIEnv *env, jstring library) {
    const char *lib_str = (*env)->GetStringUTFChars(env, library, 0);
    void *lib_addr = dlopen(lib_str, RTLD_NOW | RTLD_GLOBAL);
    if (!lib_addr) {
        throwError(env, "dlopen error: %s\n", dlerror());
        return 0;
    }
    (*env)->ReleaseStringUTFChars(env, library, lib_str);

    return lib_addr;
}

static inline
void prep_jni_cif(JNIEnv *env, ffi_cif *jni_cif, const char *jni_sig, jbyte arg_size) {
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
                throwError(env,  "unsupported JNI argument: %c\n", jni_sig_char);
                return;
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
        throwError(env, "ffi_prep_cif failed: %d\n", status);
        return;
    }
}

/*
 * call handler for functions that return an undefined symbol
 */
static
void jni_call_handler_symbol(ffi_cif *cif, void *ret, void **jargs, void *user_data){
    struct jni_call_data *call_data = user_data;

    memset(ret, 0, cif->rtype->size);
    *((void **) ret) = call_data->symaddr;
}

/*
 * call handler for functions that return a struct by value and accept a struct by value as one of its arguments
 */
static
void jni_call_handler_ret_by_value_arg_by_value(ffi_cif *cif, void *ret, void **jargs, void *user_data){
    struct jni_call_data *call_data = user_data;

    ffi_type **arg_types = call_data->cif->arg_types;
    unsigned int nargs = call_data->cif->nargs;
    ffi_type *rtype = call_data->cif->rtype;

    void **args = (nargs ? jargs + 2 : NULL);

    int i = 0;
    for (; i < nargs; i++) {
        if (arg_types[i]->type == FFI_TYPE_STRUCT) {
            //struct by value
            args[i] = *((void **) args[i]);
        }
    }

    memset(ret, 0, cif->rtype->size);

    //struct by value
    void *rval = malloc(rtype->size);
    ffi_call(call_data->cif, FFI_FN(call_data->symaddr), rval, args);
    *((void **) ret) = rval;
}

/*
 * call handler for functions that return a struct by value but none of its arguments are by value
 */
static
void jni_call_handler_ret_by_value(ffi_cif *cif, void *ret, void **jargs, void *user_data){

    struct jni_call_data *call_data = user_data;

    ffi_type **arg_types = call_data->cif->arg_types;
    unsigned int nargs = call_data->cif->nargs;
    ffi_type *rtype = call_data->cif->rtype;

    void **args = (nargs ? jargs + 2 : NULL);

    memset(ret, 0, cif->rtype->size);

    void *rval = malloc(rtype->size);
    ffi_call(call_data->cif, FFI_FN(call_data->symaddr), rval, args);
    *((void **) ret) = rval;
}

/*
 * call handler for functions that do not return a struct by value but accept a struct by value as one of its arguments
 */
static
void jni_call_handler_arg_by_value(ffi_cif *cif, void *ret, void **jargs, void *user_data){
    struct jni_call_data *call_data = user_data;

    ffi_type **arg_types = call_data->cif->arg_types;
    unsigned int nargs = call_data->cif->nargs;

    void **args = (nargs ? jargs + 2 : NULL);

    int i = 0;
    for (; i < nargs; i++) {
        if (arg_types[i]->type == FFI_TYPE_STRUCT) {
            //struct by value
            args[i] = *((void **) args[i]);
        }
    }

    memset(ret, 0, cif->rtype->size);
    ffi_call(call_data->cif, FFI_FN(call_data->symaddr), ret, args);
}

/*
 * call handler for functions that do not return a struct by value and do not accept a struct by value as one of its arguments
 */
static
void jni_call_handler_no_by_value(ffi_cif *cif, void *ret, void **jargs, void *user_data) {

    struct jni_call_data *call_data = user_data;

    void **args = (call_data->cif->nargs ? jargs + 2 : NULL);

    memset(ret, 0, cif->rtype->size);
    ffi_call(call_data->cif, FFI_FN(call_data->symaddr), ret, args);
}

static
inline
int create_closure(JNIEnv *env,
                    const char *symstr,
                    void *symaddr,
                    const char *jni_sig,
                    jbyte argSize,
                    ffi_cif *cif,
                    JNINativeMethod *jniMethods_i) {
    void *jni_func;
    ffi_closure *closure = ffi_closure_alloc(sizeof(ffi_closure), &jni_func);
    if (closure) {

        ffi_cif *jni_cif = malloc(sizeof(ffi_cif));
        prep_jni_cif(env, jni_cif, jni_sig, argSize);

        struct jni_call_data *call_data = malloc(sizeof(struct jni_call_data));
        call_data->symaddr = symaddr;

        void (*jni_call_handler) (ffi_cif *cif, void *ret, void **args, void *user_data);

        if(cif){
            //symbol is a function
            call_data->cif = cif;

            //check for struct by value as argument
            int jni_call_type= NO_BYVAL;
            int i = 0;
            for (; i < cif->nargs; i++) {
                if (cif->arg_types[i]->type == FFI_TYPE_STRUCT) {
                    jni_call_type |= ARG_BYVAL;
                    break;
                }
            }

            //check for struct by value as return
            if (cif->rtype->type == FFI_TYPE_STRUCT) {
                jni_call_type |= RET_BYVAL;
            }

            switch(jni_call_type){
                case NO_BYVAL:
                    jni_call_handler = &jni_call_handler_no_by_value;
                    break;
                case ARG_BYVAL:
                    jni_call_handler = &jni_call_handler_arg_by_value;
                    break;
                case RET_BYVAL:
                    jni_call_handler = &jni_call_handler_ret_by_value;
                    break;
                case BYVAL:
                    jni_call_handler = &jni_call_handler_ret_by_value_arg_by_value;
                    break;
            }
        } else {
            //symbol is not defined
            jni_call_handler = &jni_call_handler_symbol;
        }

        ffi_status status = ffi_prep_closure_loc(closure, jni_cif, jni_call_handler, call_data, jni_func);

        if (status == FFI_OK) {
            jniMethods_i->name = (char *) symstr;
            jniMethods_i->signature = (char *) jni_sig;
            jniMethods_i->fnPtr = jni_func;

            return 0;
        } else {
            throwError(env, "ffi_prep_closure_loc failed: %d\n", status);
            return -1;
        }
    }
    else {
        throwError(env, "ffi_closure_alloc failed: %s\n", symstr);
        return -1;
    }
}

JNIEXPORT
void
JNICALL Java_org_freedesktop_jaccall_JNI_link(JNIEnv *env, jclass clazz, jstring library,
                                                jclass headerClazz, jobjectArray symbols,
                                                jbyteArray argumentSizes,
                                                jobjectArray jniSignatures,
                                                jlongArray ffiCallInterfaces) {
    int ret;
    void *libaddr = find_libaddr(env, library);
    if(!libaddr){
        return;
    }

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
            throwError(env, "dlsym failed: %s\n", err);
            return;
        }

        jstring jniSignature = (jstring) (*env)->GetObjectArrayElement(env, jniSignatures, i);
        const char *jni_sig = (*env)->GetStringUTFChars(env, jniSignature, 0);
        jbyte argSize = argSizes[i];
        ffi_cif *cif = (ffi_cif *) (intptr_t) ffi_cifs[i];
        JNINativeMethod *jniMethods_i = &jniMethods[i];

        ret = create_closure(env, symstr, symaddr, jni_sig, argSize, cif, jniMethods_i);
        if(ret == -1){
            return;
        }
    }

    (*env)->RegisterNatives(env, headerClazz, jniMethods, symbolsCount);
}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1void(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_void;
}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1sint8(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_sint8;
}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1uint8(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_uint8;
}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1sint16(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_sint16;

}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1uint16(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_uint16;

}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1sint32(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_sint32;

}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1uint32(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_uint32;

}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1slong(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_slong;

}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1ulong(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_ulong;

}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1sint64(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_sint64;

}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1uint64(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_uint64;

}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1float(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_float;

}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1double(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_double;

}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1pointer(JNIEnv *env, jclass clazz) {
    return (jlong) (intptr_t) &ffi_type_pointer;
}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1struct(JNIEnv *env, jclass clazz,
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
       throwError(env, "ffi_type struct failed.\n");
       return 0;
    }

    return (jlong) (intptr_t) struct_description;
}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1union(JNIEnv *env, jclass clazz,
                                                            jlongArray ffi_types) {
    //TODO use calloc
    ffi_type *struct_description = malloc(sizeof(ffi_type));
    jlong *struct_ctypes = (*env)->GetLongArrayElements(env, ffi_types, 0);
    int nro_fields = (*env)->GetArrayLength(env, ffi_types);

    //TODO use calloc
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

    return (jlong) (intptr_t) struct_description;
}

JNIEXPORT
jint
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1type_1struct_1size(JNIEnv *env, jclass clazz,
                                                                   jlong ffi_struct_type) {
    ffi_type *struct_description = (ffi_type *) (intptr_t) ffi_struct_type;
    return (jint) struct_description->size;
}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1callInterface(JNIEnv *env, jclass clazz,
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
    if (ffi_prep_cif(cif, FFI_DEFAULT_ABI, (unsigned int) nro_args, (ffi_type *) (intptr_t) ffi_return_type, args) != FFI_OK) {
        throwError(env, "ffi_prep_cif failed.\n");
        return 0;
    }

    return (jlong) (intptr_t) cif;
}

static
void func_ptr_handler(ffi_cif *jni_cif, void *ret, void **jargs, void *user_data) {

    ffi_cif *cif = user_data;

    ffi_type **arg_types = cif->arg_types;
    unsigned int nargs = cif->nargs;
    ffi_type *rtype = cif->rtype;

    void *func_ptr = *((void **) jargs[2]);

    void **args = nargs ? jargs + 3 : NULL;

    //TODO (optional speed improvement) analyse cif in advance and check for struct types, so we can avoid struct_type loops if not needed
    int i = 0;
    for (; i < nargs; i++) {
        if (arg_types[i]->type == FFI_TYPE_STRUCT) {
            //struct by value
            args[i] = *((void **) args[i]);
        }
    }

    memset(ret, 0, jni_cif->rtype->size);

    if (rtype->type == FFI_TYPE_STRUCT) {
        //struct by value
        void *rval = malloc(rtype->size);
        ffi_call(cif, FFI_FN(func_ptr), rval, args);
        *((void **) ret) = rval;
    } else {
        ffi_call(cif, FFI_FN(func_ptr), ret, args);
    }
}

static
inline
int create_func_ptr_closure(JNIEnv *env,
                             const char *symstr,
                             const char *jni_sig,
                             jint argSize,
                             ffi_cif *cif,
                             JNINativeMethod *jniMethods) {
    void *jni_func;
    ffi_closure *closure = ffi_closure_alloc(sizeof(ffi_closure), &jni_func);
    if (closure) {

        ffi_cif *jni_cif = malloc(sizeof(ffi_cif));

        prep_jni_cif(env, jni_cif, jni_sig, argSize);

        ffi_status status = ffi_prep_closure_loc(closure, jni_cif, &func_ptr_handler, cif, jni_func);
        if (status == FFI_OK) {
            jniMethods->name = (char *) symstr;
            jniMethods->signature = (char *) jni_sig;
            jniMethods->fnPtr = jni_func;
            return 0;
        } else {
            throwError(env, "ffi_prep_closure_loc failed: %d\n", status);
            return -1;
        }
    }
    else {
        throwError(env, "ffi_closure_alloc failed: %s\n", symstr);
        return -1;
    }
}

JNIEXPORT
void
JNICALL Java_org_freedesktop_jaccall_JNI_linkFuncPtr(JNIEnv *env, jclass clazz, jclass wrapper, jstring symbol,
                                                       jint argSize, jstring jniSignature, jlong cif) {
    const char *symstr = (*env)->GetStringUTFChars(env, symbol, 0);
    const char *jni_sig = (*env)->GetStringUTFChars(env, jniSignature, 0);
    JNINativeMethod *jniMethods = malloc(sizeof(JNINativeMethod));

    int ret = create_func_ptr_closure(env, symstr, jni_sig, argSize, (ffi_cif *) (intptr_t) cif, jniMethods);
    if(ret == -1){
        return;
    }

    (*env)->RegisterNatives(env, wrapper, jniMethods, 1);
}

jint
JNI_OnLoad(JavaVM *vm, void *reserved){
    jvm = vm;
    return JNI_VERSION_1_6;
}

//TODO implement a func_ptr_handler for each specific arguments case (speed improvement)
static
void
java_func_ptr_handler(ffi_cif *jni_cif, void *ret, void **jargs, void *user_data) {

    struct java_call_data* call_data = user_data;
    JNIEnv * env;

    const int getEnvStat = (*jvm)->GetEnv(jvm, (void **)&env, JNI_VERSION_1_6);
    if (getEnvStat == JNI_EDETACHED) {
        if ((*jvm)->AttachCurrentThread(jvm, (void **) &env, NULL) != 0) {
                throwError(env, "Failed to attach java thread in native context.");
                return;
        }
    } else if (getEnvStat == JNI_EVERSION) {
        throwError(env, "GetEnv: version not supported.");
        return;
    }

    //TODO (optional speed improvement) analyse cif in advance and check for struct types, so we can avoid this loop if not needed
    unsigned int nargs = jni_cif->nargs;
    jvalue arguments[nargs];
    int i = 0;
    for(; i < nargs; i++){
        //TODO cast to the correct *native* pointer type or we risk reading too much data when dereferencing(?).
        if(jni_cif->arg_types[i]->type == FFI_TYPE_STRUCT){
            arguments[i].j = (jlong)(intptr_t)jargs[i];
        } else {
            arguments[i] = *((jvalue*)jargs[i]);
        }
    }

    //TODO (optional speed improvement) analyse cif in advance and avoid this switch, instead use a different java_func_ptr_handler implementation
    switch(jni_cif->rtype->type) {
        case FFI_TYPE_POINTER:
        case FFI_TYPE_UINT64:
        case FFI_TYPE_SINT64:
            *((jlong*)ret) = (*env)->CallLongMethodA(env, call_data->object, call_data->mid, arguments);
            break;
        case FFI_TYPE_VOID:
            (*env)->CallVoidMethodA(env, call_data->object, call_data->mid, arguments);
            break;
        case FFI_TYPE_UINT8:
        case FFI_TYPE_SINT8:
            *((jbyte*)ret) = (*env)->CallByteMethodA(env, call_data->object, call_data->mid, arguments);
            break;
        case FFI_TYPE_UINT16:
        case FFI_TYPE_SINT16:
            *((jshort*)ret) = (*env)->CallShortMethodA(env, call_data->object, call_data->mid, arguments);
            break;
        case FFI_TYPE_INT:
        case FFI_TYPE_UINT32:
        case FFI_TYPE_SINT32:
            *((jint*)ret) = (*env)->CallIntMethodA(env, call_data->object, call_data->mid, arguments);
            break;
        case FFI_TYPE_FLOAT:
            *((jfloat*)ret) = (*env)->CallFloatMethodA(env, call_data->object, call_data->mid, arguments);
            break;
        case FFI_TYPE_DOUBLE:
            *((jdouble*)ret) = (*env)->CallDoubleMethodA(env, call_data->object, call_data->mid, arguments);
            break;
        case FFI_TYPE_STRUCT: {
            memcpy(ret, (void *)(intptr_t)(*env)->CallLongMethodA(env, call_data->object, call_data->mid, arguments), jni_cif->rtype->size );
            break;
        }
    }

    if ((*env)->ExceptionCheck(env)) {
        (*env)->ExceptionDescribe(env);
    }

    if (getEnvStat == JNI_EDETACHED) {
        (*jvm)->DetachCurrentThread(jvm);
    }
}

JNIEXPORT
jlong
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1closure(JNIEnv *env, jclass clazz, jlong cif, jobject object, jlong methodId) {
    ffi_cif *target_cif = (ffi_cif*)(intptr_t)cif;

    void *target_func;
    ffi_closure *closure = ffi_closure_alloc(sizeof(ffi_closure), &target_func);
    if (closure) {
        struct java_call_data *java_call = malloc(sizeof(struct java_call_data));
        java_call->object = (*env)->NewGlobalRef(env, object);
        java_call->mid = (jmethodID)(intptr_t) methodId;

        ffi_status status = ffi_prep_closure_loc(closure, target_cif, &java_func_ptr_handler, java_call, target_func);
        if (status != FFI_OK) {
            throwError(env, "ffi_prep_closure_loc failed: %d\n", status);
            return 0;
        }
    }
    else {
       throwError(env, "ffi_closure_alloc failed\n");
       return 0;
    }

    return (jlong)(intptr_t)target_func;
}

JNIEXPORT
void
JNICALL Java_org_freedesktop_jaccall_JNI_ffi_1closure_1free(JNIEnv *env, jclass clazz, jlong closure) {
    ffi_closure_free((void *) (intptr_t) closure);
}

JNIEXPORT
jint
JNICALL Java_org_freedesktop_jaccall_JNI_charAlignment(JNIEnv *env, jclass clazz){
    return offset_of_char;
}

/*
 * Class:     org_freedesktop_jaccall_JNI
 * Method:    shortAlignment
 * Signature: ()I
 */
JNIEXPORT
jint
JNICALL Java_org_freedesktop_jaccall_JNI_shortAlignment(JNIEnv *env, jclass clazz){
    return offset_of_short;
}

/*
 * Class:     org_freedesktop_jaccall_JNI
 * Method:    intAlignment
 * Signature: ()I
 */
JNIEXPORT
jint
JNICALL Java_org_freedesktop_jaccall_JNI_intAlignment(JNIEnv *env, jclass clazz){
    return offset_of_int;
}

/*
 * Class:     org_freedesktop_jaccall_JNI
 * Method:    longAlignment
 * Signature: ()I
 */
JNIEXPORT
jint
JNICALL Java_org_freedesktop_jaccall_JNI_longAlignment(JNIEnv *env, jclass clazz){
    return offset_of_long;
}

/*
 * Class:     org_freedesktop_jaccall_JNI
 * Method:    longLongAlignment
 * Signature: ()I
 */
JNIEXPORT
jint
JNICALL Java_org_freedesktop_jaccall_JNI_longLongAlignment(JNIEnv *env, jclass clazz){
    return offset_of_long_long;
}

/*
 * Class:     org_freedesktop_jaccall_JNI
 * Method:    pointerAlignment
 * Signature: ()I
 */
JNIEXPORT
jint
JNICALL Java_org_freedesktop_jaccall_JNI_pointerAlignment(JNIEnv *env, jclass clazz){
    return offset_of_pointer;
}

/*
 * Class:     org_freedesktop_jaccall_JNI
 * Method:    floatAlignment
 * Signature: ()I
 */
JNIEXPORT
jint
JNICALL Java_org_freedesktop_jaccall_JNI_floatAlignment(JNIEnv *env, jclass clazz){
    return offset_of_float;
}

/*
 * Class:     org_freedesktop_jaccall_JNI
 * Method:    doubleAlignment
 * Signature: ()I
 */
JNIEXPORT
jint
JNICALL Java_org_freedesktop_jaccall_JNI_doubleAlignment(JNIEnv *env, jclass clazz){
    return offset_of_double;
}

/*
 * Class:     org_freedesktop_jaccall_JNI
 * Method:    getString
 * Signature: (JI)Ljava/lang/String{   }
 */
JNIEXPORT
jstring
JNICALL Java_org_freedesktop_jaccall_JNI_getString(JNIEnv *env, jclass clazz, jlong address, jint index){
    return (*env)->NewStringUTF(env, (const char *)(intptr_t)address);
}

/*
 * Class:     org_freedesktop_jaccall_JNI
 * Method:    writeString
 * Signature: (JILjava/lang/String{   })V
 */
JNIEXPORT
void
JNICALL Java_org_freedesktop_jaccall_JNI_setString(JNIEnv *env, jclass clazz, jlong address, jint index, jstring value){
    //TODO check for NULL array in case of oom
    const char *array = (*env)->GetStringUTFChars(env, value, 0);
    jsize length = (*env)->GetStringUTFLength(env, value);
    memcpy(((void*)(intptr_t)address)+index, (void*) array, (size_t)length+1);
    (*env)->ReleaseStringUTFChars(env, value, array);
}