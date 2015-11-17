#include <jni.h>
#include <stdlib.h>
#include <stdint.h>

#include "com_github_zubnix_jaccall_JNITestUtil.h"

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    byteArrayAsPointer
 * Signature: (IIIII)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_byteArrayAsPointer(JNIEnv *env, jclass clazz,
                                                                      jint b0, jint b1, jint b2, jint b3, jint b4) {
    char* mem = (char*) malloc(sizeof(char)*5);
    mem[0] = b0;
    mem[1] = b1;
    mem[2] = b2;
    mem[3] = b3;
    mem[4] = b4;
    return (jlong)(intptr_t) mem;
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    pointerOfPointer
 * Signature: (J)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_pointerOfPointer(JNIEnv *env, jclass clazz, jlong pointer){
    void** ppointer = malloc(sizeof(void*));
    ppointer[0] = (void*)(intptr_t)pointer;
    return (jlong)(intptr_t)ppointer;
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    readCLong
 * Signature: (J)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_readCLong(JNIEnv *env, jclass clazz, jlong clong_pointer){
    return (jlong)*((long*)(intptr_t)clong_pointer);
}