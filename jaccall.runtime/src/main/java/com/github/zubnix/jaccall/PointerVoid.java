package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

final class PointerVoid extends Pointer<Void> {
    PointerVoid(final long address,
                final boolean autoFree) {
        super(Void.class,
              address,
              autoFree,
              1);
    }

    @Nonnull
    @Override
    public Void dref() {
        throw new IllegalStateException("Can not dereference void pointer.");
    }

    @Nonnull
    @Override
    public Void dref(@Nonnegative final int index) {
        throw new IllegalStateException("Can not dereference void pointer.");
    }

    @Override
    public void write(@Nonnull final Void val) {
        throw new IllegalStateException("Can not write to void pointer.");
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Void val) {
        throw new IllegalStateException("Can not write to void pointer.");
    }
}