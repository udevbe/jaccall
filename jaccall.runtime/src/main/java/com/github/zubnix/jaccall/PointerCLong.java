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
              byteBuffer,
              sizeof((CLong) null));
    }

    @Nonnull
    @Override
    public CLong dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public CLong dref(@Nonnegative final int index) {
        final long clong;
        if (this.typeSize == 8) {
            final LongBuffer buffer = this.byteBuffer.asLongBuffer();
            buffer.rewind();
            buffer.position(index);
            clong = buffer.get();
        }
        else {
            final IntBuffer buffer = this.byteBuffer.asIntBuffer();
            buffer.rewind();
            buffer.position(index);
            clong = buffer.get();
        }
        return new CLong(clong);
    }

    @Override
    public void write(@Nonnull final CLong val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final CLong val) {

        final long clongSize = sizeof((CLong) null);

        if (clongSize == 8) {
            //64-bit
            final LongBuffer buffer = this.byteBuffer.asLongBuffer();
            buffer.clear();
            buffer.position(index);
            buffer.put(val.longValue());
        }
        else if (clongSize == 4) {
            //32-bit
            final IntBuffer buffer = this.byteBuffer.asIntBuffer();
            buffer.clear();
            buffer.position(index);
            buffer.put(val.intValue());
        }
    }
}
