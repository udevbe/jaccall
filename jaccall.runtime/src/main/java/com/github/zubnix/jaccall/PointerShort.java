package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;

final class PointerShort extends Pointer<Short> {
    PointerShort(final long address,
                 @Nonnull final ByteBuffer byteBuffer) {
        super(Short.class,
              address,
              byteBuffer,
              sizeof((Short) null));
    }

    @Nonnull
    @Override
    public Short dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public Short dref(@Nonnegative final int index) {
        final ShortBuffer buffer = this.byteBuffer.asShortBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }

    @Override
    public void write(@Nonnull final Short val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Short val) {
        final ShortBuffer buffer = this.byteBuffer.asShortBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }

    void write(final short[] val) {
        final ShortBuffer buffer = this.byteBuffer.asShortBuffer();
        buffer.clear();
        buffer.put(val);
    }
}
