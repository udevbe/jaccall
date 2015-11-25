package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class PointerVoid extends Pointer<Void> {
    PointerVoid(final Type type,
                final long address,
                final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer,
              1);
    }

    @Override
    Void dref(@Nonnull final ByteBuffer byteBuffer) {
        throw new IllegalStateException("Can not dereference void pointer.");
    }

    @Override
    Void dref(@Nonnegative final int index,
              @Nonnull final ByteBuffer byteBuffer) {
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