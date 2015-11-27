package com.github.zubnix.jaccall.runtime;


import javax.annotation.Nonnegative;
import java.nio.ByteBuffer;

public class JNI {

    /*
     * JNI ->
     */
    public static native ByteBuffer wrap(long address,
                                         long size);

    public static native long unwrap(ByteBuffer byteBuffer);
    /*
     * <- JNI
     */

    /*
     * std ->
     */
    public static native long malloc(@Nonnegative long size);

    public static native void free(final long address);

    public static native int sizeOfPointer();

    public static native int sizeOfCLong();
    /*
     * <- std
     */

    /*
     * dyncall ->
     */
}
