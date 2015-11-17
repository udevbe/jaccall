#include <jni.h>
#include <stdlib.h>
#include <stdint.h>
#include <dlfcn.h>

//FIX with cmake configuration
//#include <ffi.h>

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
                                                jobjectArray jniSignatures,
                                                jobjectArray jaccallSignatures) {
    //get handle to shared lib
    const char *libstr = (*env)->GetStringUTFChars(env, library, 0);
    void* handle = dlopen(libstr,RTLD_NOW|RTLD_GLOBAL);
    (*env)->ReleaseStringUTFChars(env, library, libstr);
    if (!handle) {
        fprintf(stderr, "dlopen error: %s\n", dlerror());
        exit(1);
    }

    int symbolsCount = (*env)->GetArrayLength(env, symbols);
    int i;
    for (i=0; i<symbolsCount; i++) {
        //lookup symbol
        jstring symbol = (jstring) (*env)->GetObjectArrayElement(env, symbols, i);
        const char *symstr = (*env)->GetStringUTFChars(env, symbol, 0);
        void* symAddress = dlsym(handle,symstr);
        (*env)->ReleaseStringUTFChars(env, symbol, symstr);
        char* err = dlerror();
        if (err) {
            fprintf(stderr, "dlsym failed: %s\n", err);
            exit(1);
        }

        //create ffi closure
        //count number of args
        jstring jniSignature = (jstring) (*env)->GetObjectArrayElement(env, jniSignatures, i);
        const char *jnistr = (*env)->GetStringUTFChars(env, jniSignature, 0);
        int nro_args = 0;
        while(jnistr[nro_args+1] != ')') {
            nro_args++;
        }
        (*env)->ReleaseStringUTFChars(env, jniSignature, jnistr);

        //fill in ffi_tye arg array
        jstring jaccallSignature = (jstring) (*env)->GetObjectArrayElement(env, jaccallSignatures, i);
        const char *jaccallstr = (*env)->GetStringUTFChars(env, jaccallSignature, 0);

        //ffi_type* args[nro_args];
        int arg = 0;
        char arg_char;
        while((arg_char = jaccallstr[arg]) != ')') {

        }
        (*env)->ReleaseStringUTFChars(env, jniSignature, jnistr);
    }
}
