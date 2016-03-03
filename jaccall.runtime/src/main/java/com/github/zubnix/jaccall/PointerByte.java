package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;

final class PointerByte extends Pointer<Byte> {

    PointerByte(final long address,
                final ByteBuffer buffer) {
        super(Byte.class,
              address,
              buffer,
              sizeof((Byte) null));
    }

    @Nonnull
    @Override
    public Byte dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public Byte dref(@Nonnegative final int index) {
        this.byteBuffer.rewind();
        this.byteBuffer.position(index);
        return this.byteBuffer.get();
    }

    @Override
    public void write(@Nonnull final Byte val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Byte val) {
        this.byteBuffer.clear();
        this.byteBuffer.position(index);
        this.byteBuffer.put(val);
    }

    void write(final byte[] val) {
        this.byteBuffer.clear();
        this.byteBuffer.put(val);
    }
}
