package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;


final class PointerDouble extends Pointer<Double> {
    PointerDouble(@Nonnull final Type type,
                  final long address,
                  @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    Double dref(@Nonnull final ByteBuffer byteBuffer) {
        return dref(0,
                    byteBuffer);
    }

    @Override
    Double dref(@Nonnegative final int index,
                       @Nonnull final ByteBuffer byteBuffer) {
        final DoubleBuffer buffer = byteBuffer.asDoubleBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }
}
