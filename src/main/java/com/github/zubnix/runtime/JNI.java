package com.github.zubnix.runtime;


import java.nio.ByteBuffer;

public class JNI {
    public native void registerLibrary(String name,
                                       ByteBuffer libMeta);
}
