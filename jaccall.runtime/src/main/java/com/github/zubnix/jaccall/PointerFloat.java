package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;

final class PointerFloat extends Pointer<Float> {
    PointerFloat(final long address,
                 @Nonnull final ByteBuffer byteBuffer) {
        super(Float.class,
              address,
              byteBuffer,
              sizeof((Float) null));
    }

    @Override
    public Float dref() {
        return dref(0);
    }

    @Override
    public Float dref(@Nonnegative final int index) {
        final FloatBuffer buffer = this.byteBuffer.asFloatBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }

    @Override
    public void write(@Nonnull final Float val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Float val) {
        final FloatBuffer buffer = this.byteBuffer.asFloatBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }
}
