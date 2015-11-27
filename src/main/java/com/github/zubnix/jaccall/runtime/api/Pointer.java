package com.github.zubnix.jaccall.runtime.api;


import com.github.zubnix.jaccall.runtime.JNI;

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


public final class Pointer<T> implements AutoCloseable {

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
        return new Pointer<>(type,
                             address,
                             JNI.wrap(address,
                                      sizeOf(type)));
    }

    public static Pointer<Void> malloc(final long size) {
        return wrap(JNI.malloc(size));
    }

    private static <U> Pointer<U> create(Class<U> type,
                                         long elementSize,
                                         int length) {
        final long size    = elementSize * length;
        final long address = JNI.malloc(size);
        return new Pointer<>(type,
                             address,
                             JNI.wrap(address,
                                      size));
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
    private final Type type;

    Pointer(@Nonnull final Type type,
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
     * <p/>
     * C equivalent:<br>
     * {@code T value = *foo}
     *
     * @return
     */
    public T dref() {
        //TODO find a good read api+impl
        return null;
    }

    /**
     * Java:<br>
     * {@code T value = foo.dref(i);}
     * <p/>
     * C equivalent:<br>
     * {@code T value = foo[i]}
     *
     * @param index
     *
     * @return
     */
    public T dref(int index) {

        //TODO find a good read api+impl

        return null;
    }

    /**
     * Java:<br>
     * {@code offsetFoo = foo.offset(i);}
     * <p/>
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

    private Class<?> toClass(){
        return toClass(this.type);
    }

    private Class<?> toClass(Type type){
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
