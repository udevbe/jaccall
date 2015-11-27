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

    public static long sizeOf(@Nonnull final DataType<?> dataType) {
        final Struct struct = dataType.getClass()
                                      .getAnnotation(Struct.class);
        return sizeOf(struct);
    }

    private static long sizeOf(@Nonnull Struct struct) {
        //TODO use jni'd dyncall to calculate struct/union size
        return JNI.sizeOfStruct();
    }

    public static long sizeOf(@Nonnull Class<?> type) {
        if (type.equals(Void.class)) {
            return Long.MAX_VALUE;
        }

        if (type.equals(Byte.class) || type.equals(byte.class)) {
            return sizeOf((Byte) null);
        }

        if (type.equals(Short.class) || type.equals(short.class)) {
            return sizeOf((Short) null);
        }

        if (type.equals(Character.class) || type.equals(char.class)) {
            return sizeOf((Character) null);
        }

        if (type.equals(Integer.class) || type.equals(int.class)) {
            return sizeOf((Integer) null);
        }

        if (type.equals(Float.class) || type.equals(float.class)) {
            return sizeOf((Float) null);
        }

        if(type.equals(Long.class)||type.equals(long.class)){
            return sizeOf((Long)null);
        }

        if (type.equals(Double.class) || type.equals(double.class)) {
            return sizeOf((Double) null);
        }

        if (type.equals(Pointer.class)) {
            return sizeOf((Pointer) null);
        }

        if (DataType.class.isAssignableFrom(type)) {
            return sizeOf(type.getAnnotation(Struct.class));
        }

        if (type.equals(CLong.class)) {
            return sizeOf((CLong) null);
        }

        throw new IllegalArgumentException("Type " + type + " does not have a known native size.");
    }
}
