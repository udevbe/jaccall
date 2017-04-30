package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public abstract class PointerFunc<T> extends Pointer<T> {
    protected PointerFunc(@Nonnull final Class<T> type,
                          final long address) {
        super(type,
              address,
              false,
              Size.sizeof((Pointer) null));
    }

    @Nonnull
    @Override
    public final T get() {
        return (T) this;
    }

    @Nonnull
    @Override
    public final T get(@Nonnegative final int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void set(@Nonnull final T val) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void set(@Nonnegative final int index,
                          @Nonnull final T val) {
        throw new UnsupportedOperationException();
    }
}
