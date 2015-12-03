#include <jni.h>
#include <stdlib.h>
#include <stdint.h>

#include "com_github_zubnix_jaccall_JNI.h"
#include "dyncall.h"
#include "dyncall_struct.h"

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
 * Method:    dcStructSize
 * Signature: (J)I
 */
JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNI_dcStructSize(JNIEnv *env, jclass clazz, jlong dcStruct) {
    return (jint) dcStructSize((DCstruct *) (intptr_t) dcStruct);
}

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    dcDefineStruct
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_dcDefineStruct(JNIEnv *env, jclass clazz, jstring signature) {
    const char *nativeSignature = (*env)->GetStringUTFChars(env, signature, 0);
    DCstruct *dcStruct = dcDefineStruct(nativeSignature);
    (*env)->ReleaseStringUTFChars(env, signature, nativeSignature);
    return (jlong) (intptr_t) dcStruct;
}

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    dcStruct_fields
 * Signature: (J)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_dcStruct_1fields(JNIEnv *env, jclass clazz, jlong dcStruct) {
    return (jlong) (intptr_t) ((DCstruct *) (intptr_t) dcStruct)->pFields;
}

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    dcField_offset
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_github_zubnix_jaccall_JNI_dcField_1offset(JNIEnv *env, jclass clazz, jlong dcField,
                                                                          jint index) {
    return (jint) ((DCfield *) (intptr_t) dcField)[(size_t) index].offset;
}