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
    //TODO split up this method & make it nicer

    //get handle to shared lib
    const char *libstr = (*env)->GetStringUTFChars(env, library, 0);
    void *libaddr = dlopen(libstr, RTLD_NOW | RTLD_GLOBAL);
    (*env)->ReleaseStringUTFChars(env, library, libstr);
    if (!libaddr) {
        fprintf(stderr, "dlopen error: %s\n", dlerror());
        exit(1);
    }

    jbyte* argSizes = (*env)->GetByteArrayElements(env, argumentSizes, 0);
    int symbolsCount = (*env)->GetArrayLength(env, symbols);
    int i;
    for (i = 0; i < symbolsCount; i++) {
        //lookup symbol
        jstring symbol = (jstring) (*env)->GetObjectArrayElement(env, symbols, i);
        const char *symstr = (*env)->GetStringUTFChars(env, symbol, 0);
        void *symaddr = dlsym(libaddr, symstr);
        (*env)->ReleaseStringUTFChars(env, symbol, symstr);
        char *err = dlerror();
        if (err) {
            fprintf(stderr, "dlsym failed: %s\n", err);
            exit(1);
        }

        //create ffi closure

        //fill in ffi_tye arg array
        jstring jaccallSignature = (jstring) (*env)->GetObjectArrayElement(env, jaccallSignatures, i);
        const char *jaccallstr = (*env)->GetStringUTFChars(env, jaccallSignature, 0);

        size_t argSize = (size_t) argSizes[i];
        ffi_type *args[(size_t) argSizes[i]];
        int arg = 0;
        char arg_char;

        int j;
        for (j = 0; j < argSize; j++) {
            char jaccallArg = jaccallstr[j];

        //C types to Jaccall mapping
//        'c'	char -> Byte, byte
//        's'	short -> Character, char, Short, short
//        'i'	int -> Integer, int
//        'j'	long -> CLong
//        'l'	long long -> Long, long
//        'f'	float -> Float, float
//        'd'	double -> Double, double
//        'p'	C pointer -> @Ptr Long, @Ptr long
//        'v'	void -> Void, void
//        't...]'   struct -> @ByVal(SomeStruct.class) Long, @ByVal(SomeStruct.class) long,
            switch (jaccallArg) {
                case 'c':
                    args[j] = &ffi_type_sint8;
                    break;
                case 's':
                    args[j] = &ffi_type_sint16;
                    break;
                case 'i':
                    args[j] = &ffi_type_sint32;
                    break;
                case 'j':
                    args[j] = &ffi_type_slong;
                    break;
                case 'l':
                    args[j] = &ffi_type_sint64;
                    break;
                case 'f':
                    args[j] = &ffi_type_float;
                    break;
                case 'd':
                    args[j] = &ffi_type_double;
                    break;
                case 'p':
                    args[j] = &ffi_type_pointer;
                    break;
                case 'v':
                    args[j] = &ffi_type_void;
                    break;
                case 't':
                    // Describe the struct to libffi. Elements are described by a
                    // NULL-terminated array of pointers to ffi_type.

                    //TODO do nested loop
//                    ffi_type* dp_elements[] = {&ffi_type_sint, &ffi_type_double, NULL};
//                    ffi_type dp_type = {.size = 0, .alignment = 0,
//                            .type = FFI_TYPE_STRUCT, .elements = dp_elements};
//
//
//                    args[j] = &ffi_type_;
                    break;
            }
        }

//        while (*jaccallstr) {
//
//            switch (*jaccallstr) {
//                case ')':
//                    break;
//            }
//
//        }
        (*env)->ReleaseStringUTFChars(env, jaccallSignature, jaccallstr);
    }
}
