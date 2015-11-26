package com.github.zubnix.jaccall.runtime;

import com.github.zubnix.jaccall.runtime.api.CLong;
import com.github.zubnix.jaccall.runtime.api.Pointer;
import com.github.zubnix.jaccall.runtime.api.Struct;

public class Size {

    public static long sizeOf(final Byte b) {
        return 1;
    }

    public static long sizeOf(final byte b) {
        return 1;
    }

    public static long sizeOf(final Character b) {
        return 2;
    }

    public static long sizeOf(final char b) {
        return 2;
    }

    public static long sizeOf(final Short b) {
        return 2;
    }

    public static long sizeOf(final short b) {
        return 2;
    }

    public static long sizeOf(final Integer b) {
        return 4;
    }

    public static long sizeOf(final int b) {
        return 4;
    }

    public static long sizeOf(final Float b) {
        return 4;
    }

    public static long sizeOf(final float b) {
        return 4;
    }

    public static long sizeOf(final Long b) {
        return 8;
    }

    public static long sizeOf(final long b) {
        return 8;
    }

    public static long sizeOf(final Double b) {
        return 8;
    }

    public static long sizeOf(final double b) {
        return 8;
    }

    public static long sizeOf(final Pointer<?> pointer) {
        return JNI.sizeOfPointer();
    }

    public static long sizeOf(final CLong cLong) {
        return JNI.sizeOfCLong();
    }

    public static long sizeOf(final DataType<?> dataType) {
        final Struct annotation = dataType.getClass()
                                          .getAnnotation(Struct.class);
        //TODO use jni'd dyncall to calculate struct/union size
        return JNI.sizeOfStruct();
    }
}
