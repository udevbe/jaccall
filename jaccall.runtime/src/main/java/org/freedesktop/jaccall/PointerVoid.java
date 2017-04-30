package org.freedesktop.jaccall;

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
    public Void get() {
        throw new IllegalStateException("Can not dereference void pointer.");
    }

    @Nonnull
    @Override
    public Void get(@Nonnegative final int index) {
        throw new IllegalStateException("Can not dereference void pointer.");
    }

    @Override
    public void set(@Nonnull final Void val) {
        throw new IllegalStateException("Can not set to void pointer.");
    }

    @Override
    public void set(@Nonnegative final int index,
                    @Nonnull final Void val) {
        throw new IllegalStateException("Can not set to void pointer.");
    }
}