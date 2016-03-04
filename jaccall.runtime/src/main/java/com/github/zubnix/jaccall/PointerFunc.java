package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static com.github.zubnix.jaccall.Size.sizeof;

public abstract class PointerFunc<T> extends Pointer<T> {
    protected PointerFunc(@Nonnull final Class<T> type,
                          final long address) {
        super(type,
              address,
              false,
              sizeof((Pointer) null));
    }

    @Nonnull
    @Override
    public final T dref() {
        return (T) this;
    }

    @Nonnull
    @Override
    public final T dref(@Nonnegative final int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void write(@Nonnull final T val) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void writei(@Nonnegative final int index,
                             @Nonnull final T val) {
        throw new UnsupportedOperationException();
    }
}
