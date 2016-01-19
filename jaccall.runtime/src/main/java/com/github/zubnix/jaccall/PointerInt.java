package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;


final class PointerInt extends Pointer<Integer> {
    PointerInt(@Nonnull final Type type,
               final long address,
               @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer,
              sizeof((Integer) null));
    }

    @Override
    Integer dref(@Nonnull final ByteBuffer byteBuffer) {
        return dref(0,
                    byteBuffer);
    }

    @Override
    Integer dref(@Nonnegative final int index,
                 @Nonnull final ByteBuffer byteBuffer) {
        final IntBuffer buffer = byteBuffer.asIntBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }

    @Override
    void write(@Nonnull final ByteBuffer byteBuffer,
               @Nonnull final Integer... val) {
        writei(byteBuffer,
               0,
               val);
    }

    @Override
    public void writei(@Nonnull final ByteBuffer byteBuffer,
                       @Nonnegative final int index,
                       @Nonnull final Integer... val) {
        final IntBuffer buffer = byteBuffer.asIntBuffer();
        buffer.clear();
        buffer.position(index);
        for (final Integer integer : val) {
            buffer.put(integer);
        }
    }
}
