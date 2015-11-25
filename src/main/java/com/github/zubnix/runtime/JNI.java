package com.github.zubnix.runtime;


import javax.annotation.Nonnegative;
import java.nio.ByteBuffer;

public class JNI {
    public native void registerLibrary(String name,
                                       ByteBuffer libMeta);

    public static native long malloc(@Nonnegative long size);

    public static native void free(final long address);
}
