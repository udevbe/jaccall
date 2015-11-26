package com.github.zubnix.jaccall.runtime.api;


import com.github.zubnix.jaccall.runtime.JNI;

import javax.annotation.Nonnull;

import static com.github.zubnix.jaccall.runtime.Size.sizeOf;


public class Pointer<T> implements AutoCloseable {

    public static Pointer<Void> wrap(final long address) {
        return wrap(Void.class,
                    address);
    }

    public static <U> Pointer<U> wrap(final Class<U> type,
                                      final long address) {
        return new Pointer<U>(type,
                              address);
    }

    public static Pointer<Void> malloc(final long size) {
        return wrap(JNI.malloc(size));
    }

    public static Pointer<Byte> ref(Byte... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Byte> pointer = new Pointer<>(Byte.class,
                                                    JNI.malloc(sizeOf(val[0]) * length));
        pointer.write(val);

        return pointer;
    }

    public static Pointer<Byte> ref(byte... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Byte> pointer = new Pointer<>(Byte.class,
                                                    JNI.malloc(sizeOf(val[0]) * length));
        pointer.write(val);

        return pointer;
    }

    public static Pointer<Short> ref(Short... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Short> pointer = new Pointer<>(Short.class,
                                                     JNI.malloc(sizeOf(val[0]) * length));
        pointer.write(val);

        return pointer;
    }

    public static Pointer<Short> ref(short... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Short> pointer = new Pointer<>(Short.class,
                                                     JNI.malloc(sizeOf(val[0]) * length));
        pointer.write(val);

        return pointer;
    }

    public static Pointer<Character> ref(Character... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Character> pointer = new Pointer<>(Character.class,
                                                         JNI.malloc(sizeOf(val[0]) * length));
        pointer.write(val);

        return pointer;
    }

    public static Pointer<Character> ref(char... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Character> pointer = new Pointer<>(Character.class,
                                                         JNI.malloc(sizeOf(val[0]) * length));
        pointer.write(val);

        return pointer;
    }

    public static Pointer<Integer> ref(Integer... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Integer> pointer = new Pointer<>(Integer.class,
                                                       JNI.malloc(sizeOf(val[0]) * length));
        pointer.write(val);

        return pointer;
    }

    public static Pointer<Integer> ref(int... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Integer> pointer = new Pointer<>(Integer.class,
                                                       JNI.malloc(sizeOf(val[0]) * length));
        pointer.write(val);

        return pointer;
    }

    private final long     address;
    @Nonnull
    private final Class<T> type;

    Pointer(@Nonnull final Class<T> type,
            final long address) {
        this.address = address;
        this.type = type;
    }

    public long address() {
        return address;
    }

    /**
     * Free the memory pointed to by this pointer.
     */
    public void close() {
        JNI.free(address());
    }

    @Nonnull
    public Class<T> getType() {
        return type;
    }

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
     * {@code offsetFoo = foo+i}
     *
     * @param bytes
     *
     * @return
     */
    public Pointer<T> offset(int bytes) {
        return new Pointer<T>(getType(),
                              address + bytes);
    }

    public <U> Pointer<U> cast(Class<U> type) {
        return new Pointer<U>(type,
                              address());
    }

    //TODO find a good write api+impl

    /*
     * Byte
     */
    public void write(final Byte... val) {

    }

    public void write(final int index,
                      final Byte val) {

    }

    public void write(final byte... val) {

    }

    public void write(final int index,
                      final byte val) {

    }

    /*
     * Short
     */
    public void write(final Short... val) {

    }

    public void write(final int index,
                      final Short val) {

    }

    public void write(final short... val) {

    }

    public void write(final int index,
                      final short val) {

    }

    /*
     * Character
     */
    public void write(final Character... val) {

    }

    public void write(final int index,
                      final Character val) {

    }

    public void write(final char... val) {

    }

    public void write(final int index,
                      final char val) {

    }

    /*
     * Integer
     */
    public void write(final Integer... val) {

    }

    public void write(final int index,
                      final Integer val) {

    }

    public void write(final int... val) {

    }

    public void write(final int index,
                      final int val) {

    }

}
