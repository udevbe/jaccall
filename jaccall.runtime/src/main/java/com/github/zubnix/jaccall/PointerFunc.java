package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;

public abstract class PointerFunc<T> extends Pointer<T> {
    protected PointerFunc(@Nonnull final Class<T> type,
                          final long address) {
        super(type,
              address,
              ByteBuffer.allocate(0),
              sizeof((Pointer) null));
    }

    @Override
    final T dref(@Nonnull final ByteBuffer byteBuffer) {
        return (T) this;
    }

    @Override
    final T dref(@Nonnegative final int index,
                 @Nonnull final ByteBuffer byteBuffer) {
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
