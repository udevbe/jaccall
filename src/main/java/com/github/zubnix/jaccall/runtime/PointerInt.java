package com.github.zubnix.jaccall.runtime;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;


final class PointerInt extends Pointer<Integer> {
    PointerInt(@Nonnull final Type type,
               final long address,
               @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    public Integer dref(@Nonnull final ByteBuffer byteBuffer) {
        final IntBuffer buffer = byteBuffer.asIntBuffer();
        buffer.rewind();
        return buffer.get();
    }

    @Override
    public Integer dref(@Nonnegative final int index,
                        @Nonnull final ByteBuffer byteBuffer) {
        final IntBuffer buffer = byteBuffer.asIntBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }
}
