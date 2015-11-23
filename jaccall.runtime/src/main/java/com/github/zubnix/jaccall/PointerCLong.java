package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;


final class PointerCLong extends Pointer<CLong> {
    PointerCLong(@Nonnull final Type type,
                 final long address,
                 @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    CLong dref(@Nonnull final ByteBuffer byteBuffer) {
        return dref(0,
                    byteBuffer);
    }

    @Override
    CLong dref(@Nonnegative final int index,
               @Nonnull final ByteBuffer byteBuffer) {
        final long clongSize = sizeof((CLong) null);
        final long clong;
        if (clongSize == 8) {
            final LongBuffer buffer = byteBuffer.asLongBuffer();
            buffer.rewind();
            buffer.position(index);
            clong = buffer.get();
        }
        else {
            final IntBuffer buffer = byteBuffer.asIntBuffer();
            buffer.rewind();
            buffer.position(index);
            clong = buffer.get();
        }
        return new CLong(clong);
    }

    @Override
    void write(@Nonnull final ByteBuffer byteBuffer,
               @Nonnull final CLong... val) {
        writei(byteBuffer,
               0,
               val);
    }

    @Override
    public void writei(@Nonnull final ByteBuffer byteBuffer,
                       @Nonnegative final int index,
                       final CLong... val) {
        if (val.length == 0) {
            return;
        }

        final long clongSize = sizeof((CLong) null);

        if (clongSize == 8) {
            //64-bit
            final LongBuffer buffer = byteBuffer.asLongBuffer();
            buffer.clear();
            buffer.position(index);
            for (final CLong cLong : val) {
                buffer.put(cLong.longValue());
            }
        }
        else if (clongSize == 4) {
            //32-bit
            final IntBuffer buffer = byteBuffer.asIntBuffer();
            buffer.clear();
            buffer.position(index);
            for (final CLong cLong : val) {
                buffer.put(cLong.intValue());
            }
        }
    }
}
