package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;


final class PointerDouble extends Pointer<Double> {
    PointerDouble(@Nonnull final Type type,
                  final long address,
                  @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer,
              sizeof((Double) null));
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

    @Override
    void write(@Nonnull final ByteBuffer byteBuffer,
               @Nonnull final Double... val) {
        writei(byteBuffer,
               0,
               val);
    }

    @Override
    public void writei(@Nonnull final ByteBuffer byteBuffer,
                       @Nonnegative final int index,
                       @Nonnull final Double... val) {
        final DoubleBuffer buffer = byteBuffer.asDoubleBuffer();
        buffer.clear();
        buffer.position(index);
        for (final Double aDouble : val) {
            buffer.put(aDouble);
        }
    }
}
