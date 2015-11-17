package com.github.zubnix.jaccall.runtime;

import com.github.zubnix.jaccall.runtime.api.CLong;
import com.github.zubnix.jaccall.runtime.api.Pointer;
import com.github.zubnix.jaccall.runtime.api.Struct;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Size {

    public static long sizeOf(@Nullable final Byte b) { return 1; }

    public static long sizeOf(final byte b) { return 1; }

    public static long sizeOf(@Nullable final Character b) { return 2; }

    public static long sizeOf(final char b) { return 2; }

    public static long sizeOf(@Nullable final Short b) { return 2; }

    public static long sizeOf(final short b) { return 2; }

    public static long sizeOf(@Nullable final Integer b) { return 4; }

    public static long sizeOf(final int b) { return 4; }

    public static long sizeOf(@Nullable final Float b) { return 4; }

    public static long sizeOf(final float b) { return 4; }

    public static long sizeOf(@Nullable final Long b) { return 8; }

    public static long sizeOf(final long b) { return 8; }

    public static long sizeOf(@Nullable final Double b) { return 8; }

    public static long sizeOf(final double b) { return 8; }

    public static long sizeOf(@Nullable final Pointer<?> pointer) { return JNI.sizeOfPointer(); }

    public static long sizeOf(@Nullable final CLong cLong) { return JNI.sizeOfCLong(); }

    public static long sizeOf(@Nullable final Void val) {
        //on gcc it's 1
        return 1;
    }

    public static long sizeOf(@Nonnull final StructType<?> dataType) {
        final Struct struct = dataType.getClass()
                                      .getAnnotation(Struct.class);
        return sizeOf(struct);
    }

    public static long sizeOf(@Nonnull Struct struct) {
        //TODO use jni'd dyncall to calculate struct/union size
        //TODO can't we calculate the size by arch at compile time and add that information?
        return JNI.sizeOfStruct();
    }
}
