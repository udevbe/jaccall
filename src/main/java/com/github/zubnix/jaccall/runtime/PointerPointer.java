package com.github.zubnix.jaccall.runtime;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;


final class PointerPointer extends Pointer<Pointer<?>> {
    PointerPointer(@Nonnull final Type type,
                   final long address,
                   @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    protected Pointer<?> dref(@Nonnull final ByteBuffer byteBuffer) {
        return null;
    }

    @Override
    protected Pointer<?> dref(@Nonnegative final int index,
                              @Nonnull final ByteBuffer byteBuffer) {
        return null;
    }
}
