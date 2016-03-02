package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.github.zubnix.jaccall.Size.sizeof;

public abstract class Pointer<T> implements AutoCloseable {

    private static final boolean ENABLE_LOG = Logger.getLogger("jaccall")
                                                    .isLoggable(Level.FINE);

    protected static final Map<Class, PointerFactory<?>> POINTER_FACTORIES = new HashMap<>(32);

    static {
        final PointerByteFactory pointerByteFactory = new PointerByteFactory();
        POINTER_FACTORIES.put(Byte.class,
                              pointerByteFactory);
        POINTER_FACTORIES.put(byte.class,
                              pointerByteFactory);
        POINTER_FACTORIES.put(CLong.class,
                              new PointerCLongFactory());
        final PointerDoubleFactory pointerDoubleFactory = new PointerDoubleFactory();
        POINTER_FACTORIES.put(Double.class,
                              pointerDoubleFactory);
        POINTER_FACTORIES.put(double.class,
                              pointerDoubleFactory);
        final PointerFloatFactory pointerFloatFactory = new PointerFloatFactory();
        POINTER_FACTORIES.put(Float.class,
                              pointerFloatFactory);
        POINTER_FACTORIES.put(float.class,
                              pointerFloatFactory);
        final PointerIntFactory pointerIntFactory = new PointerIntFactory();
        POINTER_FACTORIES.put(Integer.class,
                              pointerIntFactory);
        POINTER_FACTORIES.put(int.class,
                              pointerIntFactory);
        final PointerLongFactory pointerLongFactory = new PointerLongFactory();
        POINTER_FACTORIES.put(Long.class,
                              pointerLongFactory);
        POINTER_FACTORIES.put(long.class,
                              pointerLongFactory);
        POINTER_FACTORIES.put(Pointer.class,
                              new PointerPointerFactory());
        final PointerShortFactory pointerShortFactory = new PointerShortFactory();
        POINTER_FACTORIES.put(Short.class,
                              pointerShortFactory);
        POINTER_FACTORIES.put(short.class,
                              pointerShortFactory);
        POINTER_FACTORIES.put(String.class,
                              new PointerStringFactory());
        POINTER_FACTORIES.put(StructType.class,
                              new PointerStructFactory());
        final PointerVoidFactory pointerVoidFactory = new PointerVoidFactory();
        POINTER_FACTORIES.put(Void.class,
                              pointerVoidFactory);
        POINTER_FACTORIES.put(void.class,
                              pointerVoidFactory);
        POINTER_FACTORIES.put(JObject.class,
                              new PointerJObjectFactory());
    }

    /**
     * Wrap byte buffer in a void pointer.
     *
     * @param byteBuffer A direct byte buffer
     *
     * @return a new untyped pointer object that will use the memory pointed to by the given direct byte buffer.
     *
     * @throws IllegalArgumentException Thrown if the given byte buffer is not direct.
     * @throws NullPointerException     Thrown if the given byte buffer is null.
     */
    @Nonnull
    public static Pointer<Void> wrap(@Nonnull final ByteBuffer byteBuffer) {
        if (!Objects.requireNonNull(byteBuffer,
                                    "Argument byteByffer must not be null")
                    .isDirect()) {
            throw new IllegalArgumentException("ByteBuffer must be direct.");
        }

        return wrap(Void.class,
                    JNI.unwrap(byteBuffer),
                    byteBuffer);
    }

    /**
     * Wrap a byte buffer in a typed pointer.
     *
     * @param type       A class that represents the memory pointed to by given direct byte buffer.
     * @param byteBuffer a direct byte buffer
     * @param <U>        The Java type of the given type object.
     *
     * @return a new typed pointer object that will use the memory pointed to by the given direct byte buffer.
     *
     * @throws IllegalArgumentException Thrown if the given byte buffer is not direct.
     * @throws NullPointerException     Thrown if the given byte buffer or type is null.
     */
    @Nonnull
    public static <U> Pointer<U> wrap(@Nonnull final Class<U> type,
                                      @Nonnull final ByteBuffer byteBuffer) {
        if (!Objects.requireNonNull(byteBuffer,
                                    "Argument byteBuffer must not be null.")
                    .isDirect()) {
            throw new IllegalArgumentException("ByteBuffer must be direct.");
        }

        return wrap((Type) Objects.requireNonNull(type,
                                                  "Argument type must not be null."),
                    JNI.unwrap(byteBuffer),
                    byteBuffer);
    }

    /**
     * Wrap an address in a void pointer.
     *
     * @param address a valid memory address.
     *
     * @return a new untyped pointer object that will use the memory pointed to by the given address.
     */
    @Nonnull
    public static Pointer<Void> wrap(final long address) {
        return wrap(Void.class,
                    address);
    }

    /**
     * Wrap an address in a typed pointer.
     *
     * @param type    A class that represents the memory pointed to by given address.
     * @param address a valid memory address.
     * @param <U>     The Java type of the given type object.
     *
     * @return a new typed pointer object that will use the memory pointed to by the given address.
     *
     * @throws NullPointerException Thrown if the given type is null.
     */
    @Nonnull
    public static <U> Pointer<U> wrap(@Nonnull final Class<U> type,
                                      final long address) {
        return wrap((Type) Objects.requireNonNull(type,
                                                  "Argument type must not be null."),
                    address,
                    JNI.wrap(address,
                             Integer.MAX_VALUE));
    }

    static <U> Pointer<U> wrap(@Nonnull final Type type,
                               final long address,
                               @Nonnull final ByteBuffer byteBuffer) {

        if (ENABLE_LOG) {
            Logger.getLogger("jaccall")
                  .log(Level.FINE,
                       "Creating pointer POJO of type=" + type +
                       " with address=0x" + String.format("%016X",
                                                          address));
        }

        final Class<?> rawType = toClass(type);
        final Class<?> lookupType;

        if (StructType.class.isAssignableFrom(rawType)) {
            lookupType = StructType.class;
        }
        else if (Pointer.class.isAssignableFrom(rawType)) {
            lookupType = Pointer.class;
        }
        else {
            lookupType = rawType;
        }

        PointerFactory<?> pointerFactory = POINTER_FACTORIES.get(lookupType);
        if (pointerFactory == null) {
            //check if we're dealing with an unregistered functor type
            if (rawType.getAnnotation(Functor.class) != null) {
                try {
                    final Class<?> functorPointerFactory = rawType.getClassLoader()
                                                                  .loadClass(rawType.getName() + "_PointerFactory");
                    pointerFactory = (PointerFactory<?>) functorPointerFactory.newInstance();
                    POINTER_FACTORIES.put(lookupType,
                                          pointerFactory);
                }
                catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    throw new Error(e);
                }
            }
            else {
                throw new IllegalArgumentException("Type " + rawType + " does not have a known mapping.");
            }
        }

        return (Pointer<U>) pointerFactory.create(type,
                                                  address,
                                                  byteBuffer);
    }

    /**
     * Allocate size bytes and returns a pointer to
     * the allocated memory.  The memory is not initialized.
     * The memory is allocated on the heap and not subject to Java's GC.
     *
     * @param size The size in bytes. Must be a positive number.
     *
     * @return a new untyped pointer object that will use the newly allocated memory.
     *
     * @throws IllegalArgumentException Thrown if size argument is a negative number.
     */
    @Nonnull
    public static Pointer<Void> malloc(@Nonnegative final int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Given size argument is not a positive number.");
        }
        final long address = JNI.malloc(size);
        if (ENABLE_LOG) {
            Logger.getLogger("jaccall")
                  .log(Level.FINE,
                       "Heap allocated memory at address=0x" + String.format("%016X",
                                                                             address));
        }
        return wrap(address);
    }

    /**
     * Allocate size bytes and returns a typed pointer to
     * the allocated memory.  The memory is not initialized.
     * The memory is allocated on the heap and not subject to Java's GC.
     *
     * @param size The size in bytes
     * @param type The type of the pointer.
     *
     * @return a new untyped pointer that points to the newly allocated heap memory.
     *
     * @throws IllegalArgumentException Thrown if size argument is a negative number.
     * @throws NullPointerException     Thrown if given type argument is null.
     */
    @Nonnull
    public static <U> Pointer<U> malloc(@Nonnegative final int size,
                                        @Nonnull final Class<U> type) {
        if (size < 0) {
            throw new IllegalArgumentException("Given size argument is not a positive number.");
        }

        return wrap(Objects.requireNonNull(type,
                                           "Argument type must not be null."),
                    JNI.malloc(size));
    }

    /**
     * Allocate memory for an array of nmemb elements
     * of size bytes each and returns a pointer to the allocated memory.
     * The memory is set to zero. The memory is allocated on the heap and not subject to Java's GC.
     *
     * @param nmemb number of members
     * @param size  size of an individual member
     *
     * @return a new untyped pointer object that will use the newly allocated memory.
     *
     * @throws IllegalArgumentException Thrown if size or nmemb is a negative number.
     */
    @Nonnull
    public static Pointer<Void> calloc(@Nonnegative final int nmemb,
                                       @Nonnegative final int size) {
        if (size < 0 || nmemb < 0) {
            throw new IllegalArgumentException("Given size argument is not a positive number.");
        }

        return wrap(JNI.calloc(nmemb,
                               size));
    }

    /**
     * Allocate typed memory for an array of nmemb elements
     * of size bytes each and returns a pointer to the allocated memory.
     * The memory is set to zero. The memory is allocated on the heap and not subject to Java's GC.
     *
     * @param nmemb
     * @param size
     *
     * @return a new untyped pointer object that will use the newly allocated memory.
     *
     * @throws IllegalArgumentException Thrown if size or nmemb is a negative number.
     * @throws NullPointerException     Thrown if given type argument is null.
     */
    @Nonnull
    public static <U> Pointer<U> calloc(@Nonnegative final int nmemb,
                                        @Nonnegative final int size,
                                        @Nonnull final Class<U> type) {
        if (size < 0 || nmemb < 0) {
            throw new IllegalArgumentException("Given size or nmemb argument is not a positive number.");
        }

        return wrap(Objects.requireNonNull(type,
                                           "Argument type must not be null"),
                    JNI.calloc(nmemb,
                               size));
    }

    private static <U> Pointer<U> createStack(final Class<U> type,
                                              final int elementSize,
                                              final int length) {
        return wrap(type,
                    ByteBuffer.allocateDirect(elementSize * length));
    }

    /**
     * @param val one ore more {@code CLongs}s.
     *
     * @return a new typed pointer that will use new memory initialized with the given {@code CLongs}s.
     *
     * @throws IllegalArgumentException thrown when zero arguments are provided.
     * @throws NullPointerException     thrown if the var args val argument or one of the individual elements is null.
     */
    @Nonnull
    public static Pointer<CLong> nref(@Nonnull final CLong... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Class<? extends CLong> componentType = val[0].getClass();
        final Pointer<CLong> pointer = (Pointer<CLong>) createStack(componentType,
                                                                    sizeof((CLong) null),
                                                                    length);
        for (int i = 0; i < length; i++) {
            pointer.writei(i,
                           val[i]);
        }

        return pointer;
    }

    public static Pointer<JObject> nref(@Nonnull final JObject... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<JObject> pointer = (Pointer<JObject>) createStack(val[0].getClass(),
                                                                        sizeof(val[0]),
                                                                        length);
        pointer.write(val);

        return pointer;
    }

    /**
     * Get a pointer object that refers to the memory used by the given struct. The memory pointed to can be either
     * heap allocated memory or memory subject to Java's GC, depending on how the given struct was created.
     * <p/>
     * A struct created through a call to {@code new} will be subject to Java's GC while a struct
     * dereferenced from a pointer created with {@link #malloc(int)} or {@link #calloc(int, int)} will live on the heap
     * until it is explicitly freed with a call to {@link #close()}.
     *
     * @param val a struct.
     * @param <U> The Java type of the struct.
     *
     * @return a typed pointer that will point to the memory used by the given struct.
     *
     * @throws NullPointerException thrown if the given val argument is null.
     */
    @Nonnull
    public static <U extends StructType> Pointer<U> ref(@Nonnull final U val) {
        return (Pointer<U>) wrap(Objects.requireNonNull(val,
                                                        "Argument val must not be null")
                                        .getClass(),
                                 val.buffer());
    }

    @SafeVarargs
    @Nonnull
    public static <U extends StructType> Pointer<U> nref(@Nonnull final U... val) {

        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<U> pointer = (Pointer<U>) createStack(val[0].getClass(),
                                                            sizeof(val[0]),
                                                            length);
        pointer.write(val);

        return pointer;
    }

    /**
     * Create a pointer with newly allocated memory. The memory is initialized with the given pointers.
     * The memory is subject to Java's GC and as such should only be used in case where one would need stack
     * allocated memory.
     *
     * @param val One ore more pointers. Each pointer has to be of the same type.
     * @param <U> The Java type of the pointer.
     *
     * @return a new typed pointer that will use new memory initialized with the given pointers.
     *
     * @throws IllegalArgumentException thrown when zero arguments are provided.
     * @throws NullPointerException     thrown if the given val argument is null.
     */
    @SafeVarargs
    @Nonnull
    public static <U extends Pointer> Pointer<U> nref(@Nonnull final U... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<U> pointer = (Pointer<U>) createStack(val[0].getClass(),
                                                            sizeof((Pointer) null),
                                                            length);
        pointer.write(val);

        return pointer;
    }

    /**
     * Create a pointer with newly allocated memory. The memory is initialized with the given byte arguments.
     * The memory is subject to Java's GC and as such should only be used in case where one would need stack
     * allocated memory.
     *
     * @param val One ore more bytes.
     *
     * @return a new typed pointer object that will use new memory initialized with the given bytes.
     *
     * @throws IllegalArgumentException thrown when zero arguments are provided.
     * @throws NullPointerException     thrown if the given val argument is null.
     */
    @Nonnull
    public static Pointer<Byte> nref(@Nonnull final Byte... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Byte> pointer = createStack(Byte.class,
                                                  sizeof((Byte) null),
                                                  length);
        pointer.write(val);

        return pointer;
    }

    @Nonnull
    public static Pointer<Byte> nref(@Nonnull final byte[] val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Byte[] bytes = new Byte[val.length];
        for (int i = 0, bytesLength = bytes.length; i < bytesLength; i++) {
            bytes[i] = val[i];
        }

        return nref(bytes);
    }

    /**
     * Create a pointer with newly allocated memory. The memory is initialized with the given short arguments.
     * The memory is subject to Java's GC and as such should only be used in case where one would need stack
     * allocated memory.
     *
     * @param val One ore more shorts.
     *
     * @return a new typed pointer object that will use new memory initialized with the given shorts.
     *
     * @throws IllegalArgumentException thrown when zero arguments are provided.
     * @throws NullPointerException     thrown if the given val argument is null.
     */
    @Nonnull
    public static Pointer<Short> nref(@Nonnull final Short... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Short> pointer = createStack(Short.class,
                                                   sizeof((Short) null),
                                                   length);
        pointer.write(val);

        return pointer;
    }

    @Nonnull
    public static Pointer<Short> nref(@Nonnull final short[] val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Short[] shorts = new Short[val.length];
        for (int i = 0; i < shorts.length; i++) {
            shorts[i] = val[i];
        }

        return nref(shorts);
    }

    /**
     * Create a new pointer object with newly allocated memory. The memory is initialized with the given integer arguments.
     * The memory is subject to Java's GC and as such should only be used in case where one would need stack
     * allocated memory.
     *
     * @param val One ore more ints.
     *
     * @return a new typed pointer object that will use new memory initialized with the given ints.
     *
     * @throws IllegalArgumentException thrown when zero arguments are provided.
     * @throws NullPointerException     thrown if the given val argument is null.
     */
    @Nonnull
    public static Pointer<Integer> nref(@Nonnull final Integer... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Integer> pointer = createStack(Integer.class,
                                                     sizeof((Integer) null),
                                                     length);
        pointer.write(val);

        return pointer;
    }

    @Nonnull
    public static Pointer<Integer> nref(@Nonnull final int[] val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Integer[] ints = new Integer[val.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = val[i];
        }

        return nref(ints);
    }

    /**
     * Create a new pointer object with newly allocated memory. The memory is initialized with the given floats.
     * The memory is subject to Java's GC and as such should only be used in case where one would need stack
     * allocated memory.
     *
     * @param val One ore more floats.
     *
     * @return a new typed pointer object that will use new memory initialized with the given floats.
     *
     * @throws IllegalArgumentException thrown when zero arguments are provided.
     * @throws NullPointerException     thrown if the given val argument is null.
     */
    @Nonnull
    public static Pointer<Float> nref(@Nonnull final Float... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Float> pointer = createStack(Float.class,
                                                   sizeof((Float) null),
                                                   length);
        pointer.write(val);

        return pointer;
    }

    @Nonnull
    public static Pointer<Float> nref(@Nonnull final float[] val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Float[] floats = new Float[val.length];
        for (int i = 0, floatsLength = floats.length; i < floatsLength; i++) {
            floats[i] = val[i];
        }

        return nref(floats);
    }

    /**
     * Create a new pointer object with newly allocated memory. The memory is initialized with the given longs.
     * The memory is subject to Java's GC and as such should only be used in case where one would need stack
     * allocated memory.
     *
     * @param val One ore more longs.
     *
     * @return a new typed pointer object that will use new memory initialized with the given longs.
     *
     * @throws IllegalArgumentException thrown when zero arguments are provided.
     * @throws NullPointerException     thrown if the given val argument is null.
     */
    @Nonnull
    public static Pointer<Long> nref(@Nonnull final Long... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Long> pointer = createStack(Long.class,
                                                  sizeof((Long) null),
                                                  length);
        pointer.write(val);

        return pointer;
    }

    @Nonnull
    public static Pointer<Long> nref(@Nonnull final long[] val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Long[] longs = new Long[val.length];
        for (int i = 0, longsLength = longs.length; i < longsLength; i++) {
            longs[i] = val[i];
        }

        return nref(longs);
    }

    /**
     * Create a new pointer object with newly allocated memory. The memory is initialized with the given doubles.
     * The memory is subject to Java's GC and as such should only be used in case where one would need stack
     * allocated memory.
     *
     * @param val One ore more doubles.
     *
     * @return a new typed pointer object that will use new memory initialized with the given doubles.
     *
     * @throws IllegalArgumentException thrown when zero arguments are provided.
     * @throws NullPointerException     thrown if the given val argument is null.
     */
    @Nonnull
    public static Pointer<Double> nref(@Nonnull final Double... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Pointer<Double> pointer = createStack(Double.class,
                                                    sizeof((Double) null),
                                                    length);
        pointer.write(val);

        return pointer;
    }

    @Nonnull
    public static Pointer<Double> nref(@Nonnull final double[] val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final Double[] doubles = new Double[val.length];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = val[i];
        }

        return nref(doubles);
    }

    /**
     * Create a new pointer object with newly allocated memory. The memory is initialized with the given string.
     * The memory is subject to Java's GC and as such should only be used in case where one would need stack
     * allocated memory.
     *
     * @param val the Java string that will be copied to a zero terminated C string.
     *
     * @return a new typed pointer object that will use new memory initialized with the given string.
     */
    @Nonnull
    public static Pointer<String> nref(@Nonnull final String val) {
        final Pointer<String> pointer = createStack(String.class,
                                                    sizeof(Objects.requireNonNull(val,
                                                                                  "Argument val must not be null")),
                                                    1);
        pointer.write(val);

        return pointer;
    }

    public final long       address;
    @Nonnull
    final        ByteBuffer byteBuffer;
    @Nonnull
    final        Type       type;
    final        int        typeSize;

    Pointer(@Nonnull final Type type,
            final long address,
            @Nonnull final ByteBuffer byteBuffer,
            final int typeSize) {
        this.address = address;
        this.type = type;
        this.typeSize = typeSize;
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
    public void close() {
        if (ENABLE_LOG) {
            Logger.getLogger("jaccall")
                  .log(Level.FINE,
                       "Explicit call to free for Pointer POJO of type=" + this.type + " with address=0x" + String.format("%016X",
                                                                                                                          this.address));
        }
        JNI.free(this.address);
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
    @Nonnull
    public abstract T dref();

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
    @Nonnull
    public abstract T dref(@Nonnegative final int index);

    /**
     * Java:<br>
     * {@code offsetFoo = foo.offset(i);}
     * <p/>
     * C equivalent:<br>
     * {@code offsetFoo = foo+i;}
     *
     * @param offset
     *
     * @return
     */
    @Nonnull
    public final Pointer<T> offset(final int offset) {
        final int byteOffset = offset * this.typeSize;

        final long newAddress = this.address + byteOffset;

        this.byteBuffer.rewind();
        this.byteBuffer.position(byteOffset);

        return wrap((Type) this.type,
                    newAddress,
                    this.byteBuffer.slice());
    }

    /**
     * Pointer type cast. Cast this pointer to a pointer of a different type.
     *
     * @param type
     * @param <U>
     *
     * @return
     */
    @Nonnull
    public <U> Pointer<U> castp(@Nonnull final Class<U> type) {
        return wrap(type,
                    this.address,
                    this.byteBuffer);
    }

    /**
     * Pointer-to-pointer cast. Cast this pointer to a pointer-to-pointer of the same type.
     *
     * @return
     */
    @Nonnull
    public Pointer<Pointer<T>> castpp() {
        return wrap(new ParameterizedType() {
                        @Override
                        public Type[] getActualTypeArguments() { return new Type[]{Pointer.this.type}; }

                        @Override
                        public Type getRawType() { return Pointer.class; }

                        @Override
                        public Type getOwnerType() { return null; }
                    },
                    this.address,
                    this.byteBuffer);
    }

    public abstract void write(@Nonnull final T val);

    public abstract void writei(@Nonnegative final int index,
                                @Nonnull final T val);

    private void write(final T[] vals) {
        for (int i = 0; i < vals.length; i++) {
            writei(i,
                   vals[i]);
        }
    }

    @Nonnull
    static Class<?> toClass(@Nonnull final Type type) {
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

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (ENABLE_LOG) {
            Logger.getLogger("jaccall")
                  .log(Level.FINE,
                       "Pointer POJO of type=" + this.type + " with address=0x" + String.format("%016X",
                                                                                                this.address) + " garbage collected.");
        }
    }
}