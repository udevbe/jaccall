package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public class PointerShort extends Pointer<Short> {
    public PointerShort(@Nonnull final Type type,
                        final long address,
                        @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    Short dref(@Nonnull final ByteBuffer byteBuffer) {
        return dref(0,
                    byteBuffer);
    }

    @Override
    Short dref(@Nonnegative final int index,
               @Nonnull final ByteBuffer byteBuffer) {
        final ShortBuffer buffer = byteBuffer.asShortBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }

    @Override
    protected void write(@Nonnull final ByteBuffer byteBuffer,
                         @Nonnull final Short... val) {
        writei(byteBuffer,
               0,
               val);
    }

    @Override
    public void writei(@Nonnull final ByteBuffer byteBuffer,
                       @Nonnegative final int index,
                       final Short... val) {
        final ShortBuffer buffer = byteBuffer.asShortBuffer();
        buffer.clear();
        buffer.position(index);
        for (Short aShort : val) {
            buffer.put(aShort);
        }
    }
}