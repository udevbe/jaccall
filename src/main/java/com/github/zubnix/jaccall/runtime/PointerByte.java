package com.github.zubnix.jaccall.runtime;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class PointerByte extends Pointer<Byte> {
    PointerByte(Type type,
                final long address,
                final ByteBuffer buffer) {
        super(type,
              address,
              buffer);
    }

    @Override
    public Byte dref(@Nonnull final ByteBuffer byteBuffer) {
        byteBuffer.rewind();
        return byteBuffer.get();
    }

    @Override
    public Byte dref(@Nonnegative final int index,
                     @Nonnull final ByteBuffer buffer) {
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }
}
