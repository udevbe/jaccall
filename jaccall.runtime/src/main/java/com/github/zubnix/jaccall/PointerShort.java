package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;

public class PointerShort extends Pointer<Short> {
    public PointerShort(@Nonnull final Type type,
                        final long address,
                        @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer,
              sizeof((Short) null));
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
    public void write(@Nonnull final Short val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Short val) {
        final ShortBuffer buffer = this.byteBuffer.asShortBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }
}
