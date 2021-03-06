package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Pointer<T> implements Closeable {

    static {
        if (ConfigVariables.JACCALL_DEBUG) {
            final Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.FINE);
            final Logger jaccallLogger = Logger.getLogger("jaccall");
            jaccallLogger.setLevel(Level.FINE);
            jaccallLogger.addHandler(consoleHandler);
        }
    }

    static final boolean ENABLE_LOG = Logger.getLogger("jaccall")
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
        POINTER_FACTORIES.put(Object.class,
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
                    JNI.unwrap(byteBuffer));
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
                    false);
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
                    false);
    }

    static <U> Pointer<U> wrap(@Nonnull final Type type,
                               final long address,
                               final boolean autoFree) {

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
                                                  autoFree);
    }

    /**
     * Reallocate size bytes and returns a pointer to
     * the reallocated memory. The contents will be unchanged in the range
     * from the start of the region up to the minimum of the old and new sizes.
     * If the new size is larger than the old size, the added memory will not be
     * initialized. The added memory is not initialized.
     * The memory is reallocated on the heap and not subject to Java's GC.
     *
     * @param pointer the pointer to use as base address.
     * @param size    The new size in bytes. Must be a positive number.
     *
     * @return a new untyped pointer object that will use the newly allocated memory.
     *
     * @throws IllegalArgumentException Thrown if size argument is a negative number.
     */
    public static Pointer<Void> realloc(@Nonnull Pointer<?> pointer,
                                        @Nonnegative final int size) {
        Objects.requireNonNull(pointer,
                               "Argument pointer must not be null.");
        if (size < 0) {
            throw new IllegalArgumentException("Given size argument is not a positive number.");
        }

        final long address = JNI.realloc(pointer.address,
                                         size);

        if (ENABLE_LOG) {
            Logger.getLogger("jaccall")
                  .log(Level.FINE,
                       "Heap re-allocated memory at address=0x" + String.format("%016X",
                                                                                address));
        }

        return wrap(address);
    }

    /**
     * Reallocate size bytes and returns a pointer to
     * the reallocated memory. The contents will be unchanged in the range
     * from the start of the region up to the minimum of the old and new sizes.
     * If the new size is larger than the old size, the added memory will not be
     * initialized. The added memory is not initialized.
     * The memory is reallocated on the heap and not subject to Java's GC.
     *
     * @param pointer the pointer to use as base address.
     * @param size    The size in bytes. Must be a positive number.
     * @param type    the type of the pointer of the reallocated memory.
     *
     * @return a new untyped pointer object that will use the newly allocated memory.
     *
     * @throws IllegalArgumentException Thrown if size argument is a negative number.
     */
    public static <U> Pointer<U> realloc(@Nonnull Pointer<?> pointer,
                                         @Nonnegative final int size,
                                         @Nonnull final Class<U> type) {
        Objects.requireNonNull(pointer,
                               "Argument pointer must not be null.");
        if (size < 0) {
            throw new IllegalArgumentException("Given size argument is not a positive number.");
        }
        Objects.requireNonNull(type,
                               "Argument type must not be null.");

        final long address = JNI.realloc(pointer.address,
                                         size);

        if (ENABLE_LOG) {
            Logger.getLogger("jaccall")
                  .log(Level.FINE,
                       "Heap re-allocated memory at address=0x" + String.format("%016X",
                                                                                address));
        }

        return wrap(type,
                    address);
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
                    JNI.malloc(elementSize * length),
                    true);
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
                                                                    Size.sizeof((CLong) null),
                                                                    length);
        for (int i = 0; i < length; i++) {
            pointer.set(i,
                        val[i]);
        }

        return pointer;
    }

    public static Pointer<Object> from(@Nonnull final Object val) {
        return wrap(Object.class,
                    JNI.NewGlobalRef(val));
    }

    /**
     * Get a pointer object that refers to the memory used by the given struct. The memory pointed to can be either
     * heap allocated memory or memory subject to Java's GC, depending on how the given struct was created.
     * <p>
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
                                 val.address());
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
                                                            Size.sizeof(val[0]),
                                                            length);
        pointer.set(val);

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
                                                            Size.sizeof((Pointer) null),
                                                            length);
        pointer.set(val);

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
    public static Pointer<Byte> nref(@Nonnull final byte... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final PointerByte pointer = (PointerByte) createStack(Byte.class,
                                                              Size.sizeof((Byte) null),
                                                              length);
        //shortcut to avoid autoboxing
        pointer.set(val);

        return pointer;
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
    public static Pointer<Short> nref(@Nonnull final short... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final PointerShort pointer = (PointerShort) createStack(Short.class,
                                                                Size.sizeof((Short) null),
                                                                length);
        //shortcut to avoid autoboxing
        pointer.set(val);

        return pointer;
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
    public static Pointer<Integer> nref(@Nonnull final int... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final PointerInt pointer = (PointerInt) createStack(Integer.class,
                                                            Size.sizeof((Integer) null),
                                                            length);
        pointer.set(val);

        return pointer;
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
    public static Pointer<Float> nref(@Nonnull final float... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final PointerFloat pointer = (PointerFloat) createStack(Float.class,
                                                                Size.sizeof((Float) null),
                                                                length);
        pointer.set(val);

        return pointer;
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
    public static Pointer<Long> nref(@Nonnull final long... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final PointerLong pointer = (PointerLong) createStack(Long.class,
                                                              Size.sizeof((Long) null),
                                                              length);
        pointer.set(val);

        return pointer;
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
    public static Pointer<Double> nref(@Nonnull final double... val) {
        final int length = Objects.requireNonNull(val,
                                                  "Argument val must not be null").length;
        if (length == 0) {
            throw new IllegalArgumentException("Cannot allocate zero length array.");
        }

        final PointerDouble pointer = (PointerDouble) createStack(Double.class,
                                                                  Size.sizeof((Double) null),
                                                                  length);
        pointer.set(val);

        return pointer;
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
                                                    Size.sizeof(Objects.requireNonNull(val,
                                                                                       "Argument val must not be null")),
                                                    1);
        pointer.set(val);

        return pointer;
    }

    public final  long    address;
    private final boolean autoFree;

    @Nonnull
    final Type type;
    final int  typeSize;

    Pointer(@Nonnull final Type type,
            final long address,
            final boolean autoFree,
            final int typeSize) {
        this.type = type;
        this.address = address;
        this.autoFree = autoFree;
        this.typeSize = typeSize;
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
                       "Call to free for Pointer POJO of type=" + this.type + " with address=0x" + String.format("%016X",
                                                                                                                 this.address));
        }
        JNI.free(this.address);
    }

    /**
     * Java:<br>
     * {@code T value = foo.get();}
     * <p>
     * C equivalent:<br>
     * {@code T value = *foo}
     *
     * @return
     */
    @Nonnull
    public abstract T get();

    /**
     * Java:<br>
     * {@code T value = foo.get(i);}
     * <p>
     * C equivalent:<br>
     * {@code T value = foo[i]}
     *
     * @param index
     *
     * @return
     */
    @Nonnull
    public abstract T get(@Nonnegative final int index);

    /**
     * Java:<br>
     * {@code offsetFoo = foo.inc();}
     * <p>
     * C equivalent:<br>
     * {@code offsetFoo = foo++;}
     *
     * @param value
     *
     * @return
     */
    @Nonnull
    public final Pointer<T> inc() {
        return plus(1);
    }

    /**
     * Java:<br>
     * {@code offsetFoo = foo.dec();}
     * <p>
     * C equivalent:<br>
     * {@code offsetFoo = foo--;}
     *
     * @param value
     *
     * @return
     */
    @Nonnull
    public final Pointer<T> dec() {
        return minus(1);
    }

    /**
     * Java:<br>
     * {@code offsetFoo = foo.plus(i);}
     * <p>
     * C equivalent:<br>
     * {@code offsetFoo = foo+i;}
     *
     * @param value
     *
     * @return
     */
    @Nonnull
    public final Pointer<T> plus(final int value) {
        final int byteOffset = value * this.typeSize;

        return wrap((Type) this.type,
                    this.address + byteOffset,
                    false);
    }

    /**
     * Java:<br>
     * {@code offsetFoo = foo.minus(i);}
     * <p>
     * C equivalent:<br>
     * {@code offsetFoo = foo-i;}
     *
     * @param value
     *
     * @return
     */
    @Nonnull
    public final Pointer<T> minus(final int value) {
        final int byteOffset = -value * this.typeSize;

        return wrap((Type) this.type,
                    this.address + byteOffset,
                    false);
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
                    this.address);
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
                    false);
    }

    public abstract void set(@Nonnull final T val);

    public abstract void set(@Nonnegative final int index,
                             @Nonnull final T val);

    private void set(final T[] vals) {
        for (int i = 0; i < vals.length; i++) {
            set(i,
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
        if (this.autoFree) {
            close();
        }
    }
}