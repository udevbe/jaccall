package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;


final class PointerLong extends Pointer<Long> {
    PointerLong(final long address,
                @Nonnull final ByteBuffer byteBuffer) {
        super(Long.class,
              address,
              byteBuffer,
              sizeof((Long) null));
    }

    @Override
    public Long dref() {
        return dref(0);
    }

    @Override
    public Long dref(@Nonnegative final int index) {
        final LongBuffer buffer = this.byteBuffer.asLongBuffer();
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

    void write(final long[] val) {
        final LongBuffer buffer = this.byteBuffer.asLongBuffer();
        buffer.clear();
        buffer.put(val);
    }
}
