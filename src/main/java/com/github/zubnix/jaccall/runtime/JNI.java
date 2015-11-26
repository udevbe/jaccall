package com.github.zubnix.jaccall.runtime;


import javax.annotation.Nonnegative;

public class JNI {

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
