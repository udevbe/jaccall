package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;


final class PointerLong extends Pointer<Long> {
    PointerLong(@Nonnull final Type type,
                final long address,
                @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer,
              sizeof((Long) null));
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

    @Override
    public void write(@Nonnull final Long val) {
        writei(0,
               val);
    }

    public void writei(@Nonnegative final int index,
                       @Nonnull final Long val) {
        final LongBuffer buffer = this.byteBuffer.asLongBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }
}
