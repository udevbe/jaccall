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
    public Integer dref() {
        return dref(0);
    }

    @Override
    public Integer dref(@Nonnegative final int index) {
        final IntBuffer buffer = this.byteBuffer.asIntBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }

    @Override
    public void write(@Nonnull final Integer val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Integer val) {
        final IntBuffer buffer = this.byteBuffer.asIntBuffer();
        buffer.clear();
        buffer.position(index);
        buffer.put(val);
    }
}
