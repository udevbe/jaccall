package com.github.zubnix.jaccall.runtime;


import com.github.zubnix.jaccall.runtime.api.CLong;
import com.github.zubnix.jaccall.runtime.api.Pointer;
import com.github.zubnix.jaccall.runtime.api.Struct;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import static com.github.zubnix.jaccall.runtime.Size.sizeOf;

public abstract class Dereferencer<T> {

    private static final Dereferencer<Byte> BYTE_DEREFERENCER = new Dereferencer<Byte>() {
        @Override
        public Byte dref(@Nonnull final ByteBuffer byteBuffer) {
            byteBuffer.rewind();
            return byteBuffer.get();
        }

        @Override
        public Byte dref(@Nonnegative final int index,
                         @Nonnull final ByteBuffer buffer) {
            buffer.rewind();
            buffer.position(index);
            return buffer.get();
        }
    };

    private static final Dereferencer<Short> SHORT_DEREFERENCER = new Dereferencer<Short>() {
        @Override
        public Short dref(@Nonnull final ByteBuffer byteBuffer) {
            final ShortBuffer buffer = byteBuffer.asShortBuffer();
            buffer.rewind();
            return buffer.get();
        }

        @Override
        public Short dref(@Nonnegative final int index,
                          @Nonnull final ByteBuffer byteBuffer) {
            final ShortBuffer buffer = byteBuffer.asShortBuffer();
            buffer.rewind();
            buffer.position(index);
            return buffer.get();
        }
    };

    private static final Dereferencer<Character> CHARACTER_DEREFERENCER = new Dereferencer<Character>() {
        @Override
        public Character dref(@Nonnull final ByteBuffer byteBuffer) {
            final CharBuffer buffer = byteBuffer.asCharBuffer();
            buffer.rewind();
            return buffer.get();
        }

        @Override
        public Character dref(@Nonnegative final int index,
                              @Nonnull final ByteBuffer byteBuffer) {
            final CharBuffer buffer = byteBuffer.asCharBuffer();
            buffer.rewind();
            buffer.position(index);
            return buffer.get();
        }
    };

    private static final Dereferencer<Integer> INTEGER_DEREFERENCER = new Dereferencer<Integer>() {
        @Override
        public Integer dref(@Nonnull final ByteBuffer byteBuffer) {
            final IntBuffer buffer = byteBuffer.asIntBuffer();
            buffer.rewind();
            return buffer.get();
        }

        @Override
        public Integer dref(@Nonnegative final int index,
                            @Nonnull final ByteBuffer byteBuffer) {
            final IntBuffer buffer = byteBuffer.asIntBuffer();
            buffer.rewind();
            buffer.position(index);
            return buffer.get();
        }
    };

    private static final Dereferencer<Float> FLOAT_DEREFERENCER = new Dereferencer<Float>() {
        @Override
        public Float dref(@Nonnull final ByteBuffer byteBuffer) {
            final FloatBuffer buffer = byteBuffer.asFloatBuffer();
            buffer.rewind();
            return buffer.get();
        }

        @Override
        public Float dref(@Nonnegative final int index,
                          @Nonnull final ByteBuffer byteBuffer) {
            final FloatBuffer buffer = byteBuffer.asFloatBuffer();
            buffer.rewind();
            buffer.position(index);
            return buffer.get();
        }
    };

    private static final Dereferencer<Long> LONG_DEREFERENCER = new Dereferencer<Long>() {
        @Override
        public Long dref(@Nonnull final ByteBuffer byteBuffer) {
            final LongBuffer buffer = byteBuffer.asLongBuffer();
            buffer.rewind();
            return buffer.get();
        }

        @Override
        public Long dref(@Nonnegative final int index,
                         @Nonnull final ByteBuffer byteBuffer) {
            final LongBuffer buffer = byteBuffer.asLongBuffer();
            buffer.rewind();
            buffer.position(index);
            return buffer.get();
        }
    };

    private static final Dereferencer<Double> DOUBLE_DEREFERENCER = new Dereferencer<Double>() {
        @Override
        public Double dref(@Nonnull final ByteBuffer byteBuffer) {
            final DoubleBuffer buffer = byteBuffer.asDoubleBuffer();
            buffer.rewind();
            return buffer.get();
        }

        @Override
        public Double dref(@Nonnegative final int index,
                           @Nonnull final ByteBuffer byteBuffer) {
            final DoubleBuffer buffer = byteBuffer.asDoubleBuffer();
            buffer.rewind();
            buffer.position(index);
            return buffer.get();
        }
    };

    @Nonnull
    public static <U> Dereferencer<U> create(@Nonnull Class<U> type) {

        if (type.equals(Byte.class) || type.equals(byte.class)) {
            return (Dereferencer<U>) BYTE_DEREFERENCER;
        }

        if (type.equals(Short.class) || type.equals(short.class)) {
            return (Dereferencer<U>) SHORT_DEREFERENCER;
        }

        if (type.equals(Character.class) || type.equals(char.class)) {
            return (Dereferencer<U>) CHARACTER_DEREFERENCER;
        }

        if (type.equals(Integer.class) || type.equals(int.class)) {
            return (Dereferencer<U>) INTEGER_DEREFERENCER;
        }

        if (type.equals(Float.class) || type.equals(float.class)) {
            return (Dereferencer<U>) FLOAT_DEREFERENCER;
        }

        if (type.equals(Long.class) || type.equals(long.class)) {
            return (Dereferencer<U>) LONG_DEREFERENCER;
        }

        if (type.equals(Double.class) || type.equals(double.class)) {
            return (Dereferencer<U>) DOUBLE_DEREFERENCER;
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

        throw new IllegalArgumentException("Type " + type + " can not be read from native memory.");
    }

    public abstract T dref(@Nonnull ByteBuffer byteBuffer);

    public abstract T dref(@Nonnegative int index,
                           @Nonnull ByteBuffer byteBuffer);
}
