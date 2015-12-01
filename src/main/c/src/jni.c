//
// Created by zubzub on 11/17/15.
//

#include <jni.h>

#include "com_github_zubnix_jaccall_JNI.h"

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    wrap
 * Signature: (JJ)Ljava/nio/ByteBuffer;
 */
JNIEXPORT
jobject
JNICALL Java_com_github_zubnix_jaccall_JNI_wrap
  (JNIEnv *env, jclass clazz, jlong address, jlong size){
  }

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    unwrap
 * Signature: (Ljava/nio/ByteBuffer;)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_unwrap
  (JNIEnv *env, jclass clazz, jobject byteBuffer){
  }


/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    malloc
 * Signature: (J)I
 */
JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNI_malloc
  (JNIEnv *env, jclass clazz, jlong size){
   }

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    calloc
 * Signature: (II)I
 */
JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNI_calloc
  (JNIEnv *env, jclass clazz, jint nmemb, jint size){
   }

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT
void
JNICALL Java_com_github_zubnix_jaccall_JNI_free
  (JNIEnv *env, jclass clazz, jlong address){
   }

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    sizeOfPointer
 * Signature: ()I
 */
JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNI_sizeOfPointer
  (JNIEnv *env, jclass clazz){
    }

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    sizeOfCLong
 * Signature: ()I
 */
JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNI_sizeOfCLong
  (JNIEnv *env, jclass clazz){
    }

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    dcStructSize
 * Signature: (J)I
 */
JNIEXPORT
jint
JNICALL Java_com_github_zubnix_jaccall_JNI_dcStructSize
  (JNIEnv *env, jclass clazz, jlong dcStruct){
    }

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    dcDefineStruct
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT
jlong
JNICALL Java_com_github_zubnix_jaccall_JNI_dcDefineStruct
  (JNIEnv *env, jclass clazz, jstring signature){
   }

/*
 * Class:     com_github_zubnix_jaccall_JNI
 * Method:    dcStructFieldOffsets
 * Signature: (JLjava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;
 */
JNIEXPORT
jobject
JNICALL Java_com_github_zubnix_jaccall_JNI_dcStructFieldOffsets
  (JNIEnv *env, jclass clazz, jlong dcStruct, jobject offsets){
    }
