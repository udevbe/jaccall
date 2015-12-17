#include <jni.h>
#include <stdlib.h>
#include <stdint.h>

#include "com_github_zubnix_jaccall_JNITestUtil.h"

struct teststruct {
 char field0;
 short field1;
 int field2[3];
 int* field3;
};

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

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    readByte
 * Signature: (J)B
 */
JNIEXPORT
jbyte
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_readByte(JNIEnv *env, jclass clazz, jlong address){
    return (jlong)*((char*)(intptr_t)address);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    readFloat
 * Signature: (J)F
 */
JNIEXPORT
jfloat
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_readFloat(JNIEnv *env, jclass clazz, jlong address){
    return (jlong)*((float*)(intptr_t)address);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    readDouble
 * Signature: (J)F
 */
JNIEXPORT
jfloat
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_readDouble(JNIEnv *en, jclass clazz, jlong address){
    return (jlong)*((double*)(intptr_t)address);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    readPointer
 * Signature: (J)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_readPointer(JNIEnv *env, jclass clazz, jlong address){
    return (jlong)(intptr_t)*((void**)(intptr_t)address);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    readTestStructField0
 * Signature: (J)B
 */
JNIEXPORT
jbyte
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_readTestStructField0(JNIEnv *env, jclass clazz, jlong address){
    return (jbyte)((struct teststruct*)(intptr_t) address)->field0;
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    readTestStructField1
 * Signature: (J)S
 */
JNIEXPORT
jshort
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_readTestStructField1(JNIEnv *env, jclass clazz, jlong address){
     return (jshort)((struct teststruct*)(intptr_t) address)->field1;
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    readTestStructField2
 * Signature: (J)I
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_readTestStructField2(JNIEnv *env, jclass clazz, jlong address){
     return (jlong)(intptr_t)((struct teststruct*)(intptr_t) address)->field2;
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    readTestStructField3
 * Signature: (J)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_readTestStructField3(JNIEnv *env, jclass clazz, jlong address){
     return (jlong)((struct teststruct*)(intptr_t) address)->field3;
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    writeTestStructField0
 * Signature: (JB)V
 */
JNIEXPORT
void
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_writeTestStructField0(JNIEnv *env, jclass clazz, jlong address, jbyte field0){
    ((struct teststruct*)(intptr_t) address)->field0 = (char) field0;
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    writeTestStructField1
 * Signature: (JS)V
 */
JNIEXPORT
void
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_writeTestStructField1(JNIEnv *env, jclass clazz, jlong address, jshort field1){
    ((struct teststruct*)(intptr_t) address)->field1 = (short) field1;
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    writeTestStructField3
 * Signature: (JJ)V
 */
JNIEXPORT
void
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_writeTestStructField3(JNIEnv *env, jclass clazz, jlong address, jlong field3){
    ((struct teststruct*)(intptr_t) address)->field3 = (int*) field3;
}