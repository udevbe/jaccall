package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;

public abstract class PointerFunc<T> extends Pointer<T> {
    protected PointerFunc(@Nonnull final Class<T> type,
                          final long address,
                          final ByteBuffer buffer) {
        super(type,
              address,
              buffer,
              sizeof((Pointer) null));
    }

    @Nonnull
    @Override
    public final T dref() {
        return (T) this;
    }

    @Nonnull
    @Override
    public final T dref(@Nonnegative final int index) {
        final long val;
        if (this.typeSize == 8) {
            final LongBuffer buffer = this.byteBuffer.asLongBuffer();
            buffer.rewind();
            buffer.position(index);
            val = buffer.get();
        }
        else {
            final IntBuffer buffer = this.byteBuffer.asIntBuffer();
            buffer.rewind();
            buffer.position(index);
            val = buffer.get();
        }

        return (T) wrap(type,
                        val,
                        JNI.wrap(val,
                                 Integer.MAX_VALUE));
    }

    @Override
    public final void write(@Nonnull final T val) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void writei(@Nonnegative final int index,
                             @Nonnull final T val) {
        throw new UnsupportedOperationException();
    }
}
