package com.github.zubnix.runtime.api;


import com.github.zubnix.runtime.JNI;
import com.github.zubnix.runtime.Size;

import javax.annotation.Nonnull;

public class Pointer<T> {

    public static Pointer<Void> wrap(final long address) {
        return wrap(Void.class,
                    address);
    }

    public static <U> Pointer<U> wrap(final Class<U> type,
                                      final long address) {
        return new Pointer<U>(type,
                              address,
                              false);
    }

    public static <U> Pointer<U> ref(U... val) {
        return create(true,
                      val);
    }

    public static <U> Pointer<U> alloc(U... val) {
        return create(false,
                      val);
    }

    private static <U> Pointer<U> create(final boolean autoFree,
                                         final U... val) {
        long totalSize = 0;
        for (U u : val) {
            totalSize += Size.sizeOf(u);
        }

        final Pointer<U> pointer = new Pointer<U>((Class<U>) val.getClass()
                                                                .getDeclaringClass(),
                                                  JNI.malloc(totalSize),
                                                  autoFree);

        for (int i = 0; i < val.length; i++) {
            final U u = val[i];
            pointer.write(i,
                          u);
        }

        return pointer;
    }

    private final long     address;
    @Nonnull
    private final Class<T> type;
    private final boolean  autoFree;

    Pointer(@Nonnull final Class<T> type,
            final long address,
            final boolean autoFree) {
        this.address = address;
        this.type = type;
        this.autoFree = autoFree;
    }

    public long address() {
        return address;
    }

    @Nonnull
    public Class<T> getType() {
        return type;
    }

    public T dref() {
        //TODO find a good read api+impl
        return null;
    }

    public T dref(int index) {
        //TODO find a good read api+impl

        return null;
    }

    public Pointer<T> offset(int bytes) {
        return new Pointer<T>(getType(),
                              address + bytes,
                              false);
    }

    public <U> Pointer<U> cast(Class<U> type) {
        return new Pointer<U>(type,
                              address(),
                              false);
    }

    public void free() {
        if (autoFree) {
            throw new IllegalStateException("'Stack' allocated pointer should not be freed");
        }
        JNI.free(address());
    }

    //TODO find a good write api+impl

    private void write(final Object val) {

    }

    private void write(final int index,
                       final Object val) {

    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (autoFree) {
            JNI.free(address());
        }
    }
}
