package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public abstract class PointerFunc<T> extends Pointer<T> {
    protected PointerFunc(@Nonnull final Class<T> type,
                          final long address) {
        super(type,
              address,
              ByteBuffer.allocate(0));
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

    @SafeVarargs
    @Override
    final void write(@Nonnull final ByteBuffer byteBuffer,
                     @Nonnull final T... val) {
        throw new UnsupportedOperationException();
    }

    @SafeVarargs
    @Override
    final void writei(@Nonnull final ByteBuffer byteBuffer,
                      @Nonnegative final int index,
                      final T... val) {
        throw new UnsupportedOperationException();
    }
}
