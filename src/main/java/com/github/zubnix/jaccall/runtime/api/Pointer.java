package com.github.zubnix.jaccall.runtime.api;


import com.github.zubnix.jaccall.runtime.JNI;
import com.github.zubnix.jaccall.runtime.PointerByte;
import com.github.zubnix.jaccall.runtime.PointerCLong;
import com.github.zubnix.jaccall.runtime.PointerChar;
import com.github.zubnix.jaccall.runtime.PointerDouble;
import com.github.zubnix.jaccall.runtime.PointerFloat;
import com.github.zubnix.jaccall.runtime.PointerInt;
import com.github.zubnix.jaccall.runtime.PointerLong;
import com.github.zubnix.jaccall.runtime.PointerPointer;
import com.github.zubnix.jaccall.runtime.PointerShort;
import com.github.zubnix.jaccall.runtime.PointerStruct;
import com.github.zubnix.jaccall.runtime.PointerVoid;
import com.github.zubnix.jaccall.runtime.StructType;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import static com.github.zubnix.jaccall.runtime.Size.sizeOf;


public abstract class Pointer<T> implements AutoCloseable {

    public static Pointer<Void> wrap(@Nonnull final ByteBuffer byteBuffer) {
        if (!byteBuffer.isDirect()) {
            throw new IllegalArgumentException("ByteBuffer must be direct.");
        }

        return wrap(Void.class,
                    JNI.unwrap(byteBuffer));
    }

    public static <U> Pointer<U> wrap(@Nonnull final Type type,
                                      @Nonnull final ByteBuffer byteBuffer) {
        return wrap(type,
                    JNI.unwrap(byteBuffer));
    }

    public static Pointer<Void> wrap(final long address) {
        return wrap(Void.class,
                    address);
    }

    public static <U> Pointer<U> wrap(@Nonnull final Type type,
                                      final long address) {

        final Class<?> rawType = toClass(type);

        if (rawType.equals(Void.class)) {
            final long size = sizeOf((Void) null);
            return (Pointer<U>) new PointerVoid(type,
                                                address,
                                                JNI.wrap(address,
                                                         size));
        }

        if (rawType.equals(Byte.class) || rawType.equals(byte.class)) {
            final long size = sizeOf((Byte) null);
            return (Pointer<U>) new PointerByte(type,
                                                address,
                                                JNI.wrap(address,
                                                         size));
        }

        if (rawType.equals(Short.class) || rawType.equals(short.class)) {
            final long size = sizeOf((Short) null);
            return (Pointer<U>) new PointerShort(type,
                                                 address,
                                                 JNI.wrap(address,
                                                          size));
        }

        if (rawType.equals(Character.class) || rawType.equals(char.class)) {
            final long size = sizeOf((Character) null);
            return (Pointer<U>) new PointerChar(type,
                                                address,
                                                JNI.wrap(address,
                                                         size));
        }

        if (rawType.equals(Integer.class) || rawType.equals(int.class)) {
            final long size = sizeOf((Integer) null);
            return (Pointer<U>) new PointerInt(type,
                                               address,
                                               JNI.wrap(address,
                                                        size));
        }

        if (rawType.equals(Float.class) || rawType.equals(float.class)) {
            final long size = sizeOf((Float) null);
            return (Pointer<U>) new PointerFloat(type,
                                                 address,
                                                 JNI.wrap(address,
                                                          size));
        }

        if (rawType.equals(Long.class) || rawType.equals(long.class)) {
            final long size = sizeOf((Long) null);
            return (Pointer<U>) new PointerLong(type,
                                                address,
                                                JNI.wrap(address,
                                                         size));
        }

        if (rawType.equals(Double.class) || rawType.equals(double.class)) {
            final long size = sizeOf((Double) null);
            return (Pointer<U>) new PointerDouble(type,
                                                  address,
                                                  JNI.wrap(address,
                                                           size));
        }

        if (rawType.equals(Pointer.class)) {
            final long size = sizeOf((Pointer) null);
            return (Pointer<U>) new PointerPointer(type,
                                                   address,
                                                   JNI.wrap(address,
                                                            size));
        }

        if (StructType.class.isAssignableFrom(rawType)) {
            final long size = sizeOf(rawType.getAnnotation(Struct.class));
            return (Pointer<U>) new PointerStruct(type,
                                                  address,
                                                  JNI.wrap(address,
                                                           size));
        }

        if (rawType.equals(CLong.class)) {
            final long size = sizeOf((CLong) null);
            return (Pointer<U>) new PointerCLong(type,
                                                 address,
                                                 JNI.wrap(address,
                                                          size));
        }

        throw new IllegalArgumentException("Type " + rawType + " does not have a known native size.");
    }

    public static Pointer<Void> malloc(final long size) {
        return wrap(JNI.malloc(size));
    }

    private static <U> Pointer<U> create(Class<U> type,
                                         long elementSize,
                                         int length) {
        return wrap(type,
                    JNI.malloc(elementSize * length));
    }

    public static Pointer<Byte> ref(@Nonnull byte... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Byte> pointer = create(Byte.class,
                                             sizeOf((Byte) null),
                                             length);
        pointer.write(val);

        return pointer;
    }

    public static Pointer<Short> ref(@Nonnull short... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Short> pointer = create(Short.class,
                                              sizeOf((Short) null),
                                              length);
        pointer.write(val);

        return pointer;
    }

    public static Pointer<Character> ref(@Nonnull char... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Character> pointer = create(Character.class,
                                                  sizeOf((Character) null),
                                                  length);
        pointer.write(val);

        return pointer;
    }

    public static Pointer<Integer> ref(@Nonnull int... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Integer> pointer = create(Integer.class,
                                                sizeOf((Integer) null),
                                                length);
        pointer.write(val);

        return pointer;
    }

    public static Pointer<Float> ref(@Nonnull float... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Float> pointer = create(Float.class,
                                              sizeOf((Float) null),
                                              length);
        pointer.write(val);

        return pointer;
    }

    public static Pointer<Long> ref(@Nonnull long... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Long> pointer = create(Long.class,
                                             sizeOf((Long) null),
                                             length);
        pointer.write(val);

        return pointer;
    }

    private final long       address;
    @Nonnull
    private final ByteBuffer byteBuffer;
    @Nonnull
    private final Type       type;

    protected Pointer(@Nonnull final Type type,
                      final long address,
                      @Nonnull final ByteBuffer byteBuffer) {
        this.address = address;
        this.type = type;
        this.byteBuffer = byteBuffer.order(ByteOrder.nativeOrder());
    }

    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final Pointer<?> pointer = (Pointer<?>) o;

        return this.address == pointer.address;
    }

    @Override
    public int hashCode() { return (int) (this.address ^ (this.address >>> 32)); }

    public long address() { return this.address; }

    /**
     * Free the memory pointed to by this pointer.
     */
    public void close() { JNI.free(this.address); }

    @Nonnull
    public Type getType() { return this.type; }

    /**
     * Java:<br>
     * {@code T value = foo.dref();}
     * <p>
     * C equivalent:<br>
     * {@code T value = *foo}
     *
     * @return
     */
    public T dref() {
        return dref(this.byteBuffer);
    }

    protected abstract T dref(@Nonnull ByteBuffer byteBuffer);

    /**
     * Java:<br>
     * {@code T value = foo.dref(i);}
     * <p>
     * C equivalent:<br>
     * {@code T value = foo[i]}
     *
     * @param index
     *
     * @return
     */
    public T dref(@Nonnegative int index) {
        return dref(index,
                    this.byteBuffer);
    }

    protected abstract T dref(@Nonnegative int index,
                              @Nonnull ByteBuffer byteBuffer);

    /**
     * Java:<br>
     * {@code offsetFoo = foo.offset(i);}
     * <p>
     * C equivalent:<br>
     * {@code offsetFoo = foo+i;}
     *
     * @param bytes
     *
     * @return
     */
    public Pointer<T> offset(int bytes) {
        return wrap(this.type,
                    this.address + bytes);
    }

    public <U> Pointer<U> cast(Class<U> type) {
        return wrap(type,
                    this.address);
    }

    //TODO find a good write api+impl

    /*
     * Byte
     */
    public void write(final byte... val) {
        this.byteBuffer.clear();
        this.byteBuffer.put(val);
    }

    public void write(final int index,
                      final byte val) {
        this.byteBuffer.clear();
        this.byteBuffer.position(index);
        this.byteBuffer.put(val);
    }

    /*
     * Short
     */
    public void write(final short... val) {
        final ShortBuffer buffer = this.byteBuffer.asShortBuffer();
        buffer.clear();
        buffer.put(val);
    }

    public void write(final int index,
                      final short val) {
        final ShortBuffer buffer = this.byteBuffer.asShortBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }

    /*
     * Character
     */
    public void write(final char... val) {
        final CharBuffer buffer = this.byteBuffer.asCharBuffer();
        buffer.clear();
        buffer.put(val);
    }

    public void write(final int index,
                      final char val) {
        final CharBuffer buffer = this.byteBuffer.asCharBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }

    /*
     * Integer
     */
    public void write(final int... val) {
        final IntBuffer buffer = this.byteBuffer.asIntBuffer();
        buffer.clear();
        buffer.put(val);
    }

    public void write(final int index,
                      final int val) {
        final IntBuffer buffer = this.byteBuffer.asIntBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }

    /*
     * Float
     */
    public void write(final float... val) {
        final FloatBuffer buffer = this.byteBuffer.asFloatBuffer();
        buffer.clear();
        buffer.put(val);
    }

    public void write(final int index,
                      final float val) {
        final FloatBuffer buffer = this.byteBuffer.asFloatBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }

    /*
     * Integer
     */
    public void write(final long... val) {
        final LongBuffer buffer = this.byteBuffer.asLongBuffer();
        buffer.clear();
        buffer.put(val);
    }

    public void write(final int index,
                      final long val) {
        final LongBuffer buffer = this.byteBuffer.asLongBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }

    /*
     * Pointer
     */
    public void write(final Pointer... val) {

        final LongBuffer buffer = this.byteBuffer.asLongBuffer();
        buffer.clear();
        buffer.put(val);
    }

    public void write(final int index,
                      final Pointer val) {

        final LongBuffer buffer = this.byteBuffer.asLongBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);

    }

    private Class<?> toClass() {
        return toClass(this.type);
    }

    private static Class<?> toClass(Type type) {
        final Class<?> rawType;
        if (type instanceof Class) {
            rawType = (Class<?>) type;
        }
        else if (type instanceof ParameterizedType) {
            return toClass(((ParameterizedType) type).getRawType());
        }
        else {
            throw new UnsupportedOperationException("Type " + type + " is not supported.");
        }

        return rawType;
    }

}
