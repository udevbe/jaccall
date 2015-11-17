package com.github.zubnix.jaccall.runtime;

import com.github.zubnix.jaccall.runtime.api.Pointer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;


public class PointerDouble extends Pointer<Double> {
    public PointerDouble(@Nonnull final Type type,
                         final long address,
                         @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    public Double dref(@Nonnull final ByteBuffer byteBuffer) {
        final DoubleBuffer buffer = byteBuffer.asDoubleBuffer();
        buffer.rewind();
        return buffer.get();
    }

    @Override
    public Double dref(@Nonnegative final int index,
                       @Nonnull final ByteBuffer byteBuffer) {
        final DoubleBuffer buffer = byteBuffer.asDoubleBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }
}
