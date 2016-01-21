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

    @Nonnull
    @Override
    public Short dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public Short dref(@Nonnegative final int index) {
        final ShortBuffer buffer = this.byteBuffer.asShortBuffer();
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
