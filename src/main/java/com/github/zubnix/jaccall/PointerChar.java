package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;


final class PointerChar extends Pointer<Character> {
    PointerChar(@Nonnull final Type type,
                final long address,
                @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    Character dref(@Nonnull final ByteBuffer byteBuffer) {
        return dref(0,
                    byteBuffer);
    }

    @Override
    Character dref(@Nonnegative final int index,
                   @Nonnull final ByteBuffer byteBuffer) {
        final CharBuffer buffer = byteBuffer.asCharBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }

    @Override
    protected void write(@Nonnull final ByteBuffer byteBuffer,
                         @Nonnull final Character... val) {
        writei(byteBuffer,
               0,
               val);
    }

    @Override
    public void writei(@Nonnull final ByteBuffer byteBuffer,
                       @Nonnegative final int index,
                       final Character... val) {
        final CharBuffer buffer = byteBuffer.asCharBuffer();
        buffer.clear();
        buffer.position(index);
        for (Character character : val) {
            buffer.put(character);
        }
    }
}
