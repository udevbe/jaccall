package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class PointerByte extends Pointer<Byte> {
    PointerByte(final Type type,
                final long address,
                final ByteBuffer buffer) {
        super(type,
              address,
              buffer);
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
    protected void write(@Nonnull final ByteBuffer byteBuffer,
                         @Nonnull final Byte... val) {
        writei(byteBuffer,
               0,
               val);
    }

    @Override
    public void writei(@Nonnull final ByteBuffer byteBuffer,
                       @Nonnegative final int index,
                       final Byte... val) {
        byteBuffer.clear();
        byteBuffer.position(index);
        for (final Byte aByte : val) {
            byteBuffer.put(aByte);
        }
    }
}
