#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <stdint.h>
#include <dlfcn.h>
#include <ffi.h>

#include "com_github_zubnix_jaccall_JNI.h"

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    wrap
 * Signature: (JJ)Ljava/nio/ByteBuffer;
 */
JNIEXPORT
jobject
JNICALL Java_com_github_zubnix_jaccall_JNI_wrap(JNIEnv *env, jclass clazz, jlong address, jlong size) {
    return (*env)->NewDirectByteBuffer(env, (void *) (intptr_t) address, (size_t) size);
}

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    unwrap
 * Signature: (Ljava/nio/ByteBuffer;)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_unwrap(JNIEnv *env, jclass clazz, jobject byteBuffer) {
    return (jlong) (intptr_t) (*env)->GetDirectBufferAddress(env, byteBuffer);
}


/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    malloc
 * Signature: (J)I
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_malloc(JNIEnv *env, jclass clazz, jint size) {
    return (jlong) (intptr_t) malloc((size_t) size);
}

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    calloc
 * Signature: (II)I
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_calloc(JNIEnv *env, jclass clazz, jint nmemb, jint size) {
    return (jlong) (intptr_t) calloc((size_t) nmemb, (size_t) size);
}

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT
void
JNICALL Java_com_github_zubnix_jaccall_JNI_free(JNIEnv *env, jclass clazz, jlong address) {
    free((void *) (intptr_t) address);
}

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    sizeOfPointer
 * Signature: ()I
 */
JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNI_sizeOfPointer(JNIEnv *env, jclass clazz) {
    return (jint) sizeof(void *);
}

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    sizeOfCLong
 * Signature: ()I
 */
JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNI_sizeOfCLong(JNIEnv *env, jclass clazz) {
    return (jint) sizeof(long);
}

static inline
void *
find_libaddr(JNIEnv *env, jstring library) {
    const char *libstr = (*env)->GetStringUTFChars(env, library, 0);
    void *libaddr = dlopen(libstr, RTLD_NOW | RTLD_GLOBAL);
    (*env)->ReleaseStringUTFChars(env, library, libstr);
    if (!libaddr) {
        fprintf(stderr, "dlopen error: %s\n", dlerror());
        exit(1);
    }
    return libaddr;
}

static inline
void *
find_symaddr(JNIEnv *env, void *libaddr, jstring symbol) {
    const char *symstr = (*env)->GetStringUTFChars(env, symbol, 0);
    void *symaddr = dlsym(libaddr, symstr);
    (*env)->ReleaseStringUTFChars(env, symbol, symstr);
    char *err = dlerror();
    if (err) {
        fprintf(stderr, "dlsym failed: %s\n", err);
        exit(1);
    }
    return symaddr;
}

static inline
void
parse_struct(const char *jaccallstr,
             int *jaccall_str_index,
             ffi_type *struct_description) {

    int mark = *jaccall_str_index;
    int nro_fields = 0;
    int in_struct = 1;
    char struct_field;

    //get nro fields
    while(in_struct){
        struct_field = jaccallstr[*jaccall_str_index];
        *(jaccall_str_index)++;

        if (in_struct == 1) {
            //not in a nested struct, so incr. field count
            nro_fields++;
        }

        if(struct_field == 't') {
            //begin of nested struct
            in_struct++;
        }
        if(struct_field == ']') {
            //end of nested or 'this' struct.
            in_struct --;
        }
    }

    //reset struct definition index
    *jaccall_str_index = mark;

    ffi_type* struct_fields[nro_fields];

    //parse actual struct description
    int field_index = 0;
    for(; field_index < nro_fields; field_index++) {
        char struct_field = jaccallstr[*jaccall_str_index];
        *(jaccall_str_index)++;

        switch (struct_field) {
            case 'c':
                struct_fields[field_index] = &ffi_type_sint8;
                break;
            case 's':
                struct_fields[field_index] = &ffi_type_sint16;
                break;
            case 'i':
                struct_fields[field_index] = &ffi_type_sint32;
                break;
            case 'j':
                struct_fields[field_index] = &ffi_type_slong;
                break;
            case 'l':
                struct_fields[field_index] = &ffi_type_sint64;
                break;
            case 'f':
                struct_fields[field_index] = &ffi_type_float;
                break;
            case 'd':
                struct_fields[field_index] = &ffi_type_double;
                break;
            case 'p':
                struct_fields[field_index] = &ffi_type_pointer;
                break;
            case 't':
                //parse nested struct description
                struct_description = (ffi_type*) malloc(sizeof(ffi_type));
                parse_struct(jaccallstr, jaccall_str_index, struct_description);
                struct_fields[field_index] = struct_description;
                break;
            case ']':
                struct_fields[field_index] = NULL;
                break;
        }
    }

    struct_description->size = 0;
    struct_description->alignment = 0;
    struct_description->type = FFI_TYPE_STRUCT;
    struct_description->elements = struct_fields;
}

static inline
void
prep_ffi_arg(const char *jaccallstr, int* jaccall_str_index, ffi_type **arg){
    char jaccall_arg;
    ffi_type *struct_description;

    //C types to Jaccall mapping
    // 'c'	char -> Byte, byte
    // 's'	short -> Character, char, Short, short
    // 'i'	int -> Integer, int
    // 'j'	long -> CLong
    // 'l'	long long -> Long, long
    // 'f'	float -> Float, float
    // 'd'	double -> Double, double
    // 'p'	C pointer -> @Ptr Long, @Ptr long
    // 'v'	void -> Void, void
    // 't...]'   struct -> @ByVal(SomeStruct.class) Long, @ByVal(SomeStruct.class) long,

    jaccall_arg = jaccallstr[*jaccall_str_index];
    *(jaccall_str_index)++;

    switch (jaccall_arg) {
        case 'c':
            *arg = &ffi_type_sint8;
            break;
        case 's':
            *arg = &ffi_type_sint16;
            break;
        case 'i':
            *arg = &ffi_type_sint32;
            break;
        case 'j':
            *arg = &ffi_type_slong;
            break;
        case 'l':
            *arg = &ffi_type_sint64;
            break;
        case 'f':
            *arg = &ffi_type_float;
            break;
        case 'd':
            *arg = &ffi_type_double;
            break;
        case 'p':
            *arg = &ffi_type_pointer;
            break;
        case 'v':
            *arg = &ffi_type_void;
            break;
        case 't':
            //parse struct description
            struct_description = (ffi_type*) malloc(sizeof(ffi_type));
            parse_struct(jaccallstr, jaccall_str_index, struct_description);
            *arg = struct_description;
            break;
    }
}

static inline
void
prep_ffi_args(const char *jaccallstr,
              int *jaccall_str_index,
              ffi_type **args,
              size_t arg_size) {
    int arg_index = 0;
    for (; arg_index < arg_size; arg_index++) {
        prep_ffi_arg(jaccallstr, jaccall_str_index, &args[arg_index]);
    }
}

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    link
 * Signature: (Ljava/lang/String;Ljava/lang/Class;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V
 */
JNIEXPORT
void
JNICALL Java_com_github_zubnix_jaccall_JNI_link(JNIEnv *env,
                                                jclass clazz,
                                                jstring library,
                                                jclass headerClazz,
                                                jobjectArray symbols,
                                                jbyteArray argumentSizes,
                                                jobjectArray jniSignatures,
                                                jobjectArray jaccallSignatures) {
    //get handle to shared lib
    void *libaddr = find_libaddr(env, library);
    jbyte *argSizes = (*env)->GetByteArrayElements(env, argumentSizes, 0);
    int symbolsCount = (*env)->GetArrayLength(env, symbols);
    int i = 0;

    for (; i < symbolsCount; i++) {
        //lookup symbol (function pointer)
        jstring symbol = (jstring) (*env)->GetObjectArrayElement(env, symbols, i);
        void *symaddr = find_symaddr(env, libaddr, symbol);

        //setup ffi call
        size_t arg_size = (size_t) argSizes[i];
        ffi_type **args = (ffi_type **)malloc(sizeof(ffi_type*) * arg_size);

        jstring jaccallSignature = (jstring) (*env)->GetObjectArrayElement(env, jaccallSignatures, i);

        const char *jaccallstr = (*env)->GetStringUTFChars(env, jaccallSignature, 0);
        int jaccall_str_index = 0;

        prep_ffi_args(jaccallstr, &jaccall_str_index, args, arg_size);

        ffi_type** return_type = (ffi_type**)malloc(sizeof(ffi_type*));
        prep_ffi_arg(jaccallstr, &jaccall_str_index, return_type);

        (*env)->ReleaseStringUTFChars(env, jaccallSignature, jaccallstr);

        ffi_cif* cif = (ffi_cif*) malloc(sizeof(ffi_cif));
        ffi_status status = ffi_prep_cif(cif, FFI_DEFAULT_ABI, (unsigned int) arg_size, *return_type, args);
        if (status != FFI_OK) {
            fprintf(stderr, "ffi_prep_cif failed: %d\n", status);
            exit(1);
        }

        //TODO setup ffi closure that calls our ffi_cif with arguments it gets from Java
    }

    //TODO call jni registerNatives with our closure
}
