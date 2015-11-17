package com.github.zubnix.jaccall.runtime;

import com.github.zubnix.jaccall.runtime.api.Pointer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;


public class PointerLong extends Pointer<Long> {
    public PointerLong(@Nonnull final Type type,
                       final long address,
                       @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    public Long dref(@Nonnull final ByteBuffer byteBuffer) {
        final LongBuffer buffer = byteBuffer.asLongBuffer();
        buffer.rewind();
        return buffer.get();
    }

    @Override
    public Long dref(@Nonnegative final int index,
                     @Nonnull final ByteBuffer byteBuffer) {
        final LongBuffer buffer = byteBuffer.asLongBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }
}
