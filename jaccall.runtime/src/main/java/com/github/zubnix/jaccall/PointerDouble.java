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
    public Double dref() {
        return dref(0);
    }

    @Override
    public Double dref(@Nonnegative final int index) {
        final DoubleBuffer buffer = this.byteBuffer.asDoubleBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }

    @Override
    public void write(@Nonnull final Double val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Double val) {
        final DoubleBuffer buffer = this.byteBuffer.asDoubleBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }
}
