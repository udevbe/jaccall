package com.github.zubnix.jaccall.runtime;

import com.github.zubnix.jaccall.runtime.api.Pointer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class PointerFloat extends Pointer<Float> {
    public PointerFloat(@Nonnull final Type type,
                        final long address,
                        @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    public Float dref(@Nonnull final ByteBuffer byteBuffer) {
        final FloatBuffer buffer = byteBuffer.asFloatBuffer();
        buffer.rewind();
        return buffer.get();
    }

    @Override
    public Float dref(@Nonnegative final int index,
                      @Nonnull final ByteBuffer byteBuffer) {
        final FloatBuffer buffer = byteBuffer.asFloatBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }
}
