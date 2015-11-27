package com.github.zubnix.jaccall.runtime;

import com.github.zubnix.jaccall.runtime.api.CLong;
import com.github.zubnix.jaccall.runtime.api.Pointer;
import com.github.zubnix.jaccall.runtime.api.Struct;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

    public static long sizeOf(@Nonnull Class<?> rawType) {

        if (rawType.equals(Void.class)) {
            return Long.MAX_VALUE;
        }

        if (rawType.equals(Byte.class) || rawType.equals(byte.class)) {
            return sizeOf((Byte) null);
        }

        if (rawType.equals(Short.class) || rawType.equals(short.class)) {
            return sizeOf((Short) null);
        }

        if (rawType.equals(Character.class) || rawType.equals(char.class)) {
            return sizeOf((Character) null);
        }

        if (rawType.equals(Integer.class) || rawType.equals(int.class)) {
            return sizeOf((Integer) null);
        }

        if (rawType.equals(Float.class) || rawType.equals(float.class)) {
            return sizeOf((Float) null);
        }

        if (rawType.equals(Long.class) || rawType.equals(long.class)) {
            return sizeOf((Long) null);
        }

        if (rawType.equals(Double.class) || rawType.equals(double.class)) {
            return sizeOf((Double) null);
        }

        if (rawType.equals(Pointer.class)) {
            return sizeOf((Pointer) null);
        }

        if (DataType.class.isAssignableFrom(rawType)) {
            return sizeOf(rawType.getAnnotation(Struct.class));
        }

        if (rawType.equals(CLong.class)) {
            return sizeOf((CLong) null);
        }

        throw new IllegalArgumentException("Type " + rawType + " does not have a known native size.");
    }
}
