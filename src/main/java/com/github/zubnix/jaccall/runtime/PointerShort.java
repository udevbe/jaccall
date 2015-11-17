package com.github.zubnix.jaccall.runtime;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

/**
 * Created by zubzub on 11/17/15.
 */
public class PointerShort extends Pointer<Short> {
    public PointerShort(@Nonnull final Type type,
                        final long address,
                        @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    public Short dref(@Nonnull final ByteBuffer byteBuffer) {
        return dref(0,
                    byteBuffer);
    }

    @Override
    public Short dref(@Nonnegative final int index,
                      @Nonnull final ByteBuffer byteBuffer) {
        final ShortBuffer buffer = byteBuffer.asShortBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }
}
