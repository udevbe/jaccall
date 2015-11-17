package com.github.zubnix.jaccall.runtime;

import com.github.zubnix.jaccall.runtime.api.Pointer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class PointerStruct<T> extends Pointer<T> {
    public PointerStruct(@Nonnull final Type type,
                         final long address,
                         @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    protected T dref(@Nonnull final ByteBuffer byteBuffer) {
        return null;
    }

    @Override
    protected T dref(@Nonnegative final int index,
                     @Nonnull final ByteBuffer byteBuffer) {
        return null;
    }
}
