#include <jni.h>
#include <stdlib.h>
#include <stdint.h>
#include <dlfcn.h>

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
 * Method:    dlopen
 * Signature: (Ljava/lang/String;I)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_open(JNIEnv *env, jclass clazz, jstring filename) {
    const char *str = (*env)->GetStringUTFChars(env, filename, 0);
    void* handle = dlopen(str,RTLD_NOW|RTLD_GLOBAL);
    (*env)->ReleaseStringUTFChars(env, filename, str);
    return (jlong)(intptr_t)handle;
}

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    dlsym
 * Signature: (JLjava/lang/String;)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_sym(JNIEnv *env, jclass clazz, jlong handle, jstring symbol){
    const char *str = (*env)->GetStringUTFChars(env, symbol, 0);
    void* sym = dlsym((void*)(intptr_t)handle,str);
    (*env)->ReleaseStringUTFChars(env, symbol, str);
    return (jlong)(intptr_t)sym;
}

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    dlclose
 * Signature: (J)I
 */
JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNI_close(JNIEnv *env, jclass clazz, jlong handle){
    return (jint) dlclose((void*)(intptr_t)handle);
}
