package com.github.zubnix.jaccall.runtime;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;


final class PointerCLong extends Pointer<CLong> {
    PointerCLong(@Nonnull final Type type,
                 final long address,
                 @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    protected CLong dref(@Nonnull final ByteBuffer byteBuffer) {
        return null;
    }

    @Override
    protected CLong dref(@Nonnegative final int index,
                         @Nonnull final ByteBuffer byteBuffer) {
        return null;
    }

}
