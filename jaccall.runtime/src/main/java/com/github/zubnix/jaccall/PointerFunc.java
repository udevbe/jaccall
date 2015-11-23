package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public abstract class PointerFunc<T extends Func> extends Pointer<T> implements Func {
    protected PointerFunc(@Nonnull final Type type,
                          final long address,
                          @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    protected T dref(@Nonnull final ByteBuffer byteBuffer) {
        return (T) this;
    }

    @Override
    protected T dref(@Nonnegative final int index,
                     @Nonnull final ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException();
    }

    @SafeVarargs
    @Override
    protected final void write(@Nonnull final ByteBuffer byteBuffer,
                               @Nonnull final T... val) {
        throw new UnsupportedOperationException();
    }

    @SafeVarargs
    @Override
    protected final void writei(@Nonnull final ByteBuffer byteBuffer,
                                @Nonnegative final int index,
                                final T... val) {
        throw new UnsupportedOperationException();
    }
}
