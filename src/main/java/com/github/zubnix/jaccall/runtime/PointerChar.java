package com.github.zubnix.jaccall.runtime;

import com.github.zubnix.jaccall.runtime.api.Pointer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;


public class PointerChar extends Pointer<Character> {
    public PointerChar(@Nonnull final Type type,
                       final long address,
                       @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    public Character dref(@Nonnull final ByteBuffer byteBuffer) {
        final CharBuffer buffer = byteBuffer.asCharBuffer();
        buffer.rewind();
        return buffer.get();
    }

    @Override
    public Character dref(@Nonnegative final int index,
                          @Nonnull final ByteBuffer byteBuffer) {
        final CharBuffer buffer = byteBuffer.asCharBuffer();
        buffer.rewind();
        buffer.position(index);
        return buffer.get();
    }
}
