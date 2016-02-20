#include <jni.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>

#include "com_github_zubnix_jaccall_JNITestUtil.h"

struct teststruct {
 char field0;
 short field1;
 int field2[3];
 int* field3;
};

struct test {
    char field0;
    short field1;
    int field2[3];
    int *field3;
    struct test_embedded {
        long long embedded_field0;
        float embedded_field1;
    } field4;
};

union testunion {
    int field0;
    float field1;
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
 * Method:    execCharTest
 * Signature: (JB)B
 */
JNIEXPORT
jbyte
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execCharTest(JNIEnv *env, jclass clazz, jlong func_ptr, jbyte value){
    return ((char(*)(char))(intptr_t)func_ptr)((char)value);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execUnsignedCharTest
 * Signature: (JB)B
 */
JNIEXPORT
jbyte
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execUnsignedCharTest(JNIEnv *env, jclass clazz, jlong func_ptr, jbyte value){
    return ((unsigned char(*)(unsigned char))(intptr_t)func_ptr)((unsigned char)value);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execShortTest
 * Signature: (JS)S
 */
JNIEXPORT
jshort
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execShortTest(JNIEnv *env, jclass clazz, jlong func_ptr, jshort value){
    return ((short(*)(short))(intptr_t)func_ptr)((short)value);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execUnsignedShortTest
 * Signature: (JS)S
 */
JNIEXPORT
jshort JNICALL
Java_com_github_zubnix_jaccall_JNITestUtil_execUnsignedShortTest(JNIEnv *env, jclass clazz, jlong func_ptr, jshort value){
    return ((unsigned short(*)(unsigned short))(intptr_t)func_ptr)((unsigned short)value);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execIntTest
 * Signature: (JI)I
 */
JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execIntTest(JNIEnv *env, jclass clazz, jlong func_ptr, jint value){
    return ((int(*)(int))(intptr_t)func_ptr)((int)value);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execIntTest
 * Signature: (JI)I
 */
JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execUnsignedIntTest(JNIEnv *env, jclass clazz, jlong func_ptr, jint value){
    return ((unsigned int(*)(unsigned int))(intptr_t)func_ptr)((unsigned int)value);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execLongTest
 * Signature: (JJ)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execLongTest(JNIEnv *env, jclass clazz, jlong func_ptr, jlong value){
    return ((long(*)(long))(intptr_t)func_ptr)((long)value);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execUnsignedLongTest
 * Signature: (JJ)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execUnsignedLongTest(JNIEnv *env, jclass clazz, jlong func_ptr, jlong value){
    return ((unsigned long(*)(unsigned long))(intptr_t)func_ptr)((unsigned long)value);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execLongLongTest
 * Signature: (JJ)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execLongLongTest(JNIEnv *env, jclass clazz, jlong func_ptr, jlong value){
    return ((long long(*)(long long))(intptr_t)func_ptr)((long long)value);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execUnsignedLongLongTest
 * Signature: (JJ)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execUnsignedLongLongTest(JNIEnv *env, jclass clazz, jlong func_ptr, jlong value){
    return ((unsigned long long(*)(unsigned long long))(intptr_t)func_ptr)((unsigned long long)value);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execFloatTest
 * Signature: (JF)F
 */
JNIEXPORT
jfloat
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execFloatTest(JNIEnv *env, jclass clazz, jlong func_ptr, jfloat value){
    return ((float(*)(float))(intptr_t)func_ptr)((float)value);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execDoubleTest
 * Signature: (JD)D
 */
JNIEXPORT
jdouble
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execDoubleTest(JNIEnv *env, jclass clazz, jlong func_ptr, jdouble value){
    return ((double(*)(double))(intptr_t)func_ptr)((double)value);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execPointerTest
 * Signature: (JJ)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execPointerTest(JNIEnv *env, jclass clazz, jlong func_ptr, jlong value){
    return (jlong)(intptr_t)((void*(*)(void*))(intptr_t)func_ptr)((void*)value);
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execStructTest
 * Signature: (JJBSJJJF)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execStructTest(JNIEnv *env, jclass clazz, jlong func_ptr, jlong tst,
                                                                  jbyte field0, jshort field1, jlong field2, jlong field3,
                                                                  jlong embedded_field0, jfloat embedded_field1){
    struct test result = ((struct test(*)(struct test *,char,short,int *,int *,long long,float))(intptr_t)func_ptr)((struct test*)(intptr_t)tst,(char)field0,(unsigned short) field1,(int*)(intptr_t)field2,(int*)(intptr_t)field3,(long long)embedded_field0,(float)embedded_field1);
    void* ret_result = malloc(sizeof(struct test));
    memcpy(ret_result, &result, sizeof(struct test));
    return (jlong)(intptr_t) ret_result;
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execStructTest2
 * Signature: (JJBSJJJF)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execStructTest2(JNIEnv *env, jclass clazz, jlong func_ptr, jlong tst,
                                                                   jbyte field0, jshort field1, jlong field2, jlong field3,
                                                                   jlong embedded_field0, jfloat embedded_field1){

    struct test *result = ((struct test*(*)(struct test,char,short,int *,int *,long long,float))(intptr_t)func_ptr)(*((struct test*)(intptr_t)tst),(char)field0,(unsigned short) field1,(int*)(intptr_t)field2,(int*)(intptr_t)field3,(long long)embedded_field0,(float)embedded_field1);
    return (jlong)(intptr_t)result;
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execUnionTest
 * Signature: (JJIF)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execUnionTest(JNIEnv *env, jclass clazz, jlong func_ptr, jlong tst, jint field0, jfloat field1){
    union testunion result = ((union testunion(*)(union testunion*, int, float))(intptr_t)func_ptr)((union testunion*)(intptr_t)tst, field0, field1);
    void* ret_result = malloc(sizeof(union testunion));
    memcpy(ret_result, &result, sizeof(union testunion));
    return (jlong)(intptr_t)ret_result;
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    execUnionTest2
 * Signature: (JJI)J
 */
JNIEXPORT
jlong JNICALL Java_com_github_zubnix_jaccall_JNITestUtil_execUnionTest2(JNIEnv *env, jclass clazz, jlong func_ptr, jlong tst, jint field0){
     return (jlong)(intptr_t)((union testunion*(*)(union testunion, int))(intptr_t)func_ptr)(*((union testunion*)(intptr_t)tst), field0);
}

struct test_function_pointer {
    long (*field0)(long);
    int (*field1[3])(int);
};

long func_long(long arg0){
    return arg0;
}

int func_int0(int arg0){
    return arg0;
}

int func_int1(int arg0){
    return arg0+1;
}

int func_int2(int arg0){
    return arg0+2;
}

/*
 * Class:     com_github_zubnix_jaccall_JNITestUtil
 * Method:    allocStructFp
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL
Java_com_github_zubnix_jaccall_JNITestUtil_allocStructFp(JNIEnv *env, jclass clazz){
    struct test_function_pointer* struct_fp = malloc(sizeof(struct test_function_pointer));
    struct_fp->field0 = &func_long;
    struct_fp->field1[0] = &func_int0;
    struct_fp->field1[1] = &func_int1;
    struct_fp->field1[2] = &func_int2;

    return (jlong)(intptr_t)struct_fp;
}
