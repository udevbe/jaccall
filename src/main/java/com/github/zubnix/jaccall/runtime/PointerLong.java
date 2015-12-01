package com.github.zubnix.jaccall.runtime;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;


final class PointerLong extends Pointer<Long> {
    PointerLong(@Nonnull final Type type,
                final long address,
                @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    Long dref(@Nonnull final ByteBuffer byteBuffer) {
        return dref(0,
                    byteBuffer);
    }

    @Override
    Long dref(@Nonnegative final int index,
              @Nonnull final ByteBuffer byteBuffer) {
        final LongBuffer buffer = byteBuffer.asLongBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }
}
