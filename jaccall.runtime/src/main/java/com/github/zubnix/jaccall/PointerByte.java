package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;

final class PointerByte extends Pointer<Byte> {

    PointerByte(final Type type,
                final long address,
                final ByteBuffer buffer) {
        super(type,
              address,
              buffer,
              sizeof((Byte) null));
    }

    @Override
    Byte dref(@Nonnull final ByteBuffer byteBuffer) {
        return dref(0,
                    byteBuffer);
    }

    @Override
    Byte dref(@Nonnegative final int index,
              @Nonnull final ByteBuffer buffer) {
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
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
}
