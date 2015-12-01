package com.github.zubnix.jaccall.runtime;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

final class PointerFloat extends Pointer<Float> {
    PointerFloat(@Nonnull final Type type,
                 final long address,
                 @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    Float dref(@Nonnull final ByteBuffer byteBuffer) {
        return dref(0,
                    byteBuffer);
    }

    @Override
    Float dref(@Nonnegative final int index,
               @Nonnull final ByteBuffer byteBuffer) {
        final FloatBuffer buffer = byteBuffer.asFloatBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }
}
