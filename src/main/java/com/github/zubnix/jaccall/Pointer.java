package com.github.zubnix.jaccall;


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


public abstract class Pointer<T> implements AutoCloseable {

    /**
     * Wrap byte buffer in a void pointer.
     *
     * @param byteBuffer A direct byte buffer
     *
     * @return a new untyped pointer object that will use the memory pointed to by the given direct byte buffer.
     */
    public static Pointer<Void> wrap(@Nonnull final ByteBuffer byteBuffer) {
        if (!byteBuffer.isDirect()) {
            throw new IllegalArgumentException("ByteBuffer must be direct.");
        }

        return wrap(Void.class,
                    JNI.unwrap(byteBuffer));
    }

    /**
     * Wrap a byte buffer in a typed pointer.
     *
     * @param type       A type object (eg. class object) that represents the memory pointed to by given direct byte buffer.
     * @param byteBuffer a direct byte buffer
     * @param <U>        The Java type of the given type object.
     *
     * @return a new typed pointer object that will use the memory pointed to by the given direct byte buffer.
     */
    public static <U> Pointer<U> wrap(@Nonnull final Type type,
                                      @Nonnull final ByteBuffer byteBuffer) {
        return wrap(type,
                    JNI.unwrap(byteBuffer));
    }

    /**
     * Wrap an address in a void pointer.
     *
     * @param address a valid memory address.
     *
     * @return a new untyped pointer object that will use the memory pointed to by the given address.
     */
    public static Pointer<Void> wrap(final long address) {
        return wrap(Void.class,
                    address);
    }

    /**
     * Wrap an address in a typed pointer.
     *
     * @param type    A type object (eg. class object) that represents the memory pointed to by given address.
     * @param address a valid memory address.
     * @param <U>     The Java type of the given type object.
     *
     * @return a new typed pointer object that will use the memory pointed to by the given address.
     */
    public static <U> Pointer<U> wrap(@Nonnull final Type type,
                                      final long address) {

        final Class<?> rawType = toClass(type);

        if (StructType.class.isAssignableFrom(rawType)) {
            final long size = Size.sizeOf((Class<? extends StructType>) rawType);
            return (Pointer<U>) new PointerStruct(type,
                                                  address,
                                                  JNI.wrap(address,
                                                           size));
        }

        if (rawType.equals(Void.class) || rawType.equals(void.class)) {
            final long size = Size.sizeOf((Void) null);
            return (Pointer<U>) new PointerVoid(type,
                                                address,
                                                JNI.wrap(address,
                                                         size));
        }

        if (rawType.equals(Byte.class) || rawType.equals(byte.class)) {
            final long size = Size.sizeOf((Byte) null);
            return (Pointer<U>) new PointerByte(type,
                                                address,
                                                JNI.wrap(address,
                                                         size));
        }

        if (rawType.equals(Short.class) || rawType.equals(short.class)) {
            final long size = Size.sizeOf((Short) null);
            return (Pointer<U>) new PointerShort(type,
                                                 address,
                                                 JNI.wrap(address,
                                                          size));
        }

        if (rawType.equals(Character.class) || rawType.equals(char.class)) {
            final long size = Size.sizeOf((Character) null);
            return (Pointer<U>) new PointerChar(type,
                                                address,
                                                JNI.wrap(address,
                                                         size));
        }

        if (rawType.equals(Integer.class) || rawType.equals(int.class)) {
            final long size = Size.sizeOf((Integer) null);
            return (Pointer<U>) new PointerInt(type,
                                               address,
                                               JNI.wrap(address,
                                                        size));
        }

        if (rawType.equals(Float.class) || rawType.equals(float.class)) {
            final long size = Size.sizeOf((Float) null);
            return (Pointer<U>) new PointerFloat(type,
                                                 address,
                                                 JNI.wrap(address,
                                                          size));
        }

        if (rawType.equals(Long.class) || rawType.equals(long.class)) {
            final long size = Size.sizeOf((Long) null);
            return (Pointer<U>) new PointerLong(type,
                                                address,
                                                JNI.wrap(address,
                                                         size));
        }

        if (rawType.equals(Double.class) || rawType.equals(double.class)) {
            final long size = Size.sizeOf((Double) null);
            return (Pointer<U>) new PointerDouble(type,
                                                  address,
                                                  JNI.wrap(address,
                                                           size));
        }

        if (rawType.equals(Pointer.class)) {
            final long size = Size.sizeOf((Pointer) null);
            return (Pointer<U>) new PointerPointer(type,
                                                   address,
                                                   JNI.wrap(address,
                                                            size));
        }

        if (rawType.equals(CLong.class)) {
            final long size = Size.sizeOf((CLong) null);
            return (Pointer<U>) new PointerCLong(type,
                                                 address,
                                                 JNI.wrap(address,
                                                          size));
        }

        throw new IllegalArgumentException("Type " + rawType + " does not have a known native size.");
    }

    /**
     * Allocate size bytes and returns a pointer to
     * the allocated memory.  The memory is not initialized.
     *
     * @param size
     *
     * @return a new untyped pointer object that will use the newly allocated memory.
     */
    public static Pointer<Void> malloc(@Nonnegative final int size) {
        return wrap(JNI.malloc(size));
    }

    /**
     * Allocate memory for an array of nmemb elements
     * of size bytes each and returns a pointer to the allocated memory.
     * The memory is set to zero.
     *
     * @param nmemb
     * @param size
     *
     * @return a new untyped pointer object that will use the newly allocated memory.
     */
    public static Pointer<Void> calloc(@Nonnegative final int nmemb,
                                       @Nonnegative final int size) {
        return wrap(JNI.calloc(nmemb,
                               size));
    }

    private static <U> Pointer<U> create(Class<U> type,
                                         int elementSize,
                                         int length) {
        return wrap(type,
                    JNI.malloc(elementSize * length));
    }


    /**
     * Create a new pointer object with newly allocated memory. The memory is initialized with the given structs.
     *
     * @param val One ore more structs. Each struct has to be of the same type.
     * @param <U> The Java type of the struct.
     *
     * @return a new typed pointer object that will use new memory initialized with the given structs.
     */
    @SafeVarargs
    @Nonnull
    public static <U extends StructType> Pointer<U> ref(@Nonnull U... val) {
        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Class<? extends StructType> componentType = val[0].getClass();
        final Pointer<U> pointer = (Pointer<U>) create(componentType,
                                                       Size.sizeOf(componentType),
                                                       length);
        pointer.write(val);

        return pointer;
    }

    /**
     * Create a new pointer object with newly allocated memory. The memory is initialized with the given pointers.
     *
     * @param val One ore more pointers. Each pointer has to be of the same type.
     * @param <U> The Java type of the pointer.
     *
     * @return a new typed pointer object that will use new memory initialized with the given pointers.
     */
    @SafeVarargs
    @Nonnull
    public static <U extends Pointer> Pointer<U> ref(@Nonnull U... val) {
        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<U> pointer = (Pointer<U>) create(val[0].getClass(),
                                                       Size.sizeOf((Pointer) null),
                                                       length);
        pointer.write(val);

        return pointer;
    }

    /**
     * Create a new pointer object with newly allocated memory. The memory is initialized with the given bytes.
     *
     * @param val One ore more bytes.
     *
     * @return a new typed pointer object that will use new memory initialized with the given bytes.
     */
    @Nonnull
    public static Pointer<Byte> ref(@Nonnull byte... val) {


        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Byte> pointer = create(Byte.class,
                                             Size.sizeOf((Byte) null),
                                             length);
        pointer.write(val);

        return pointer;
    }

    /**
     * Create a new pointer object with newly allocated memory. The memory is initialized with the given shorts.
     *
     * @param val One ore more shorts.
     *
     * @return a new typed pointer object that will use new memory initialized with the given shorts.
     */
    @Nonnull
    public static Pointer<Short> ref(@Nonnull short... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Short> pointer = create(Short.class,
                                              Size.sizeOf((Short) null),
                                              length);
        pointer.write(val);

        return pointer;
    }

    /**
     * Create a new pointer object with newly allocated memory. The memory is initialized with the given chars.
     *
     * @param val One ore more chars.
     *
     * @return a new typed pointer object that will use new memory initialized with the given chars.
     */
    @Nonnull
    public static Pointer<Character> ref(@Nonnull char... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Character> pointer = create(Character.class,
                                                  Size.sizeOf((Character) null),
                                                  length);
        pointer.write(val);

        return pointer;
    }

    /**
     * Create a new pointer object with newly allocated memory. The memory is initialized with the given ints.
     *
     * @param val One ore more ints.
     *
     * @return a new typed pointer object that will use new memory initialized with the given ints.
     */
    @Nonnull
    public static Pointer<Integer> ref(@Nonnull int... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Integer> pointer = create(Integer.class,
                                                Size.sizeOf((Integer) null),
                                                length);
        pointer.write(val);

        return pointer;
    }

    /**
     * Create a new pointer object with newly allocated memory. The memory is initialized with the given floats.
     *
     * @param val One ore more floats.
     *
     * @return a new typed pointer object that will use new memory initialized with the given floats.
     */
    @Nonnull
    public static Pointer<Float> ref(@Nonnull float... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Float> pointer = create(Float.class,
                                              Size.sizeOf((Float) null),
                                              length);
        pointer.write(val);

        return pointer;
    }

    /**
     * Create a new pointer object with newly allocated memory. The memory is initialized with the given longs.
     *
     * @param val One ore more longs.
     *
     * @return a new typed pointer object that will use new memory initialized with the given longs.
     */
    @Nonnull
    public static Pointer<Long> ref(@Nonnull long... val) {

        final int length = val.length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Long> pointer = create(Long.class,
                                             Size.sizeOf((Long) null),
                                             length);
        pointer.write(val);

        return pointer;
    }

    private final   long       address;
    @Nonnull
    private final   ByteBuffer byteBuffer;
    @Nonnull
    protected final Type       type;

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

    /**
     * Free the memory pointed to by this pointer.
     */
    public void close() { JNI.free(this.address); }

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
        return dref(this.byteBuffer);
    }

    abstract T dref(@Nonnull ByteBuffer byteBuffer);

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
    public T dref(@Nonnegative int index) {
        return dref(index,
                    this.byteBuffer);
    }

    abstract T dref(@Nonnegative int index,
                    @Nonnull ByteBuffer byteBuffer);

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

    /**
     * type cast
     *
     * @param type
     * @param <U>
     *
     * @return
     */
    public <U> U tCast(@Nonnull Class<U> type) {
        //primitive "fast" paths
        if (type.equals(Long.class) || type.equals(long.class)) {
            return (U) Long.valueOf(this.address);
        }
        if (type.equals(Double.class) || type.equals(double.class)) {
            return (U) Double.valueOf(Double.longBitsToDouble(this.address));
        }
        if (type.equals(Integer.class) || type.equals(int.class)) {
            return (U) Integer.valueOf((int) this.address);
        }
        if (type.equals(Float.class) || type.equals(float.class)) {
            return (U) Float.valueOf(Float.intBitsToFloat((int) this.address));
        }
        if (type.equals(Short.class) || type.equals(short.class)) {
            return (U) Short.valueOf((short) this.address);
        }
        if (type.equals(Character.class) || type.equals(char.class)) {
            return (U) Character.valueOf((char) this.address);
        }
        if (type.equals(Byte.class) || type.equals(byte.class)) {
            return (U) Byte.valueOf((byte) this.address);
        }
        if (type.equals(Void.class) || type.equals(void.class)) {
            throw new IllegalArgumentException("Can not cast to incomplete type void.");
        }

        final ByteBuffer addressBuffer = ByteBuffer.allocate(8)
                                                   .order(ByteOrder.nativeOrder());
        final LongBuffer longBuffer = addressBuffer.asLongBuffer();
        longBuffer.clear();
        longBuffer.put(this.address);
        addressBuffer.rewind();
        return ptCast(type).dref(addressBuffer);
    }

    /**
     * Pointer type cast. Cast this pointer to a pointer of a different type.
     *
     * @param type
     * @param <U>
     *
     * @return
     */
    public <U> Pointer<U> ptCast(@Nonnull Class<U> type) {
        return wrap(type,
                    this.address);
    }

    /**
     * Pointer-to-pointer cast. Cast this pointer to a pointer-to-pointer of the same type.
     *
     * @return
     */
    public Pointer<Pointer<T>> ppCast() {
        final Class<? extends Pointer> thisClass = getClass();
        return wrap(new ParameterizedType() {
                        @Override
                        public Type[] getActualTypeArguments() { return new Type[]{Pointer.this.type}; }

                        @Override
                        public Type getRawType() { return thisClass; }

                        @Override
                        public Type getOwnerType() { return null; }
                    },
                    this.address);
    }

    /*
     * Byte
     */
    public void write(@Nonnull final byte... val) {
        write(0,
              val);
    }

    public void write(@Nonnegative final int index,
                      final byte... val) {
        this.byteBuffer.clear();
        this.byteBuffer.position(index);
        this.byteBuffer.put(val);
    }

    /*
     * Short
     */
    public void write(@Nonnull final short... val) {
        write(0,
              val);
    }

    public void write(@Nonnegative final int index,
                      final short... val) {
        final ShortBuffer buffer = this.byteBuffer.asShortBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }

    /*
     * Character
     */
    public void write(@Nonnull final char... val) {
        write(0,
              val);
    }

    public void write(@Nonnegative final int index,
                      final char... val) {
        final CharBuffer buffer = this.byteBuffer.asCharBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }

    /*
     * Integer
     */
    public void write(@Nonnull final int... val) {
        write(0,
              val);
    }

    public void write(@Nonnegative final int index,
                      final int... val) {
        final IntBuffer buffer = this.byteBuffer.asIntBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }

    /*
     * Float
     */
    public void write(@Nonnull final float... val) {
        write(0,
              val);
    }

    public void write(@Nonnegative final int index,
                      final float... val) {
        final FloatBuffer buffer = this.byteBuffer.asFloatBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }

    /*
     * Integer
     */
    public void write(@Nonnull final long... val) {
        write(0,
              val);
    }

    public void write(@Nonnegative final int index,
                      final long... val) {
        final LongBuffer buffer = this.byteBuffer.asLongBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }

    /*
     * Pointer
     */
    public void write(@Nonnull final Pointer<?>... val) {
        write(0,
              val);
    }

    public void write(@Nonnegative final int index,
                      @Nonnull final Pointer<?>... val) {
        final long pointerSize = Size.sizeOf((Pointer) null);
        if (pointerSize == 8) {
            //64-bit
            final LongBuffer buffer = this.byteBuffer.asLongBuffer();
            buffer.clear();
            buffer.position(index);
            for (Pointer<?> pointer : val) {
                buffer.put(pointer.tCast(Long.class));
            }
        }
        else if (pointerSize == 4) {
            //32-bit
            final IntBuffer buffer = this.byteBuffer.asIntBuffer();
            buffer.clear();
            buffer.position(index);
            for (Pointer<?> pointer : val) {
                buffer.put(pointer.tCast(Integer.class));
            }
        }
    }

    /*
     * CLong
     */
    public void write(@Nonnull final CLong... val) {
        write(0,
              val);
    }

    public void write(@Nonnegative final int index,
                      @Nonnull final CLong... val) {
        if (val.length == 0) {
            return;
        }

        final long clongSize = Size.sizeOf((CLong) null);

        if (clongSize == 8) {
            //64-bit
            final LongBuffer buffer = this.byteBuffer.asLongBuffer();
            buffer.clear();
            buffer.position(index);
            for (CLong cLong : val) {
                buffer.put(cLong.longValue());
            }
        }
        else if (clongSize == 4) {
            //32-bit
            final IntBuffer buffer = this.byteBuffer.asIntBuffer();
            buffer.clear();
            buffer.position(index);
            for (CLong cLong : val) {
                buffer.put(cLong.intValue());
            }
        }
    }

    /*
     * StructType
     */

    @SafeVarargs
    public final <U extends StructType> void write(@Nonnull final U... val) {
        write(0,
              val);
    }


    @SafeVarargs
    public final <U extends StructType> void write(@Nonnegative final int index,
                                                   @Nonnull final U... val) {
        if (val.length == 0) {
            return;
        }

        this.byteBuffer.clear();
        final long structTypeSize = Size.sizeOf(val[0].getClass());
        this.byteBuffer.position((int) (index * structTypeSize));
        for (StructType structType : val) {
            structType.buffer()
                      .rewind();
            structType.buffer()
                      .limit((int) structTypeSize);
            this.byteBuffer.put(structType.buffer());
        }
    }

    protected static Class<?> toClass(Type type) {
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