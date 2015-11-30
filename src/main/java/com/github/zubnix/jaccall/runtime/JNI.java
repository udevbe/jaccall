package com.github.zubnix.jaccall.runtime;


import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

class JNI {

    /*
     * JNI ->
     */
    static native ByteBuffer wrap(long address,
                                  @Nonnegative long size);

    static native long unwrap(@Nonnull ByteBuffer byteBuffer);
    /*
     * <- JNI
     */

    /*
     * std ->
     */
    static native long malloc(@Nonnegative long size);

    static native long calloc(@Nonnegative final long size);

    static native void free(final long address);

    static native int sizeOfPointer();

    static native int sizeOfCLong();
    /*
     * <- std
     */

    /*
     * dyncall ->
     */
}
