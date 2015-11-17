package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import static com.github.zubnix.jaccall.Size.sizeOf;


final class PointerPointer extends Pointer<Pointer> {
    PointerPointer(@Nonnull final Type type,
                   final long address,
                   @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    Pointer<?> dref(@Nonnull final ByteBuffer byteBuffer) {
        return dref(0,
                    byteBuffer);
    }

    @Override
    Pointer<?> dref(@Nonnegative final int index,
                    @Nonnull final ByteBuffer byteBuffer) {
        final long size = sizeOf((Pointer) null);
        final long val;
        if (size == 8) {
            final LongBuffer buffer = byteBuffer.asLongBuffer();
            buffer.rewind();
            buffer.position(index);
            val = buffer.get();
        }
        else {
            final IntBuffer buffer = byteBuffer.asIntBuffer();
            buffer.rewind();
            buffer.position(index);
            val = buffer.get();
        }

        //handle untyped pointers
        final Type type;
        if (this.type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) this.type;
            type = parameterizedType.getActualTypeArguments()[0];
        }
        else {
            //untyped pointer
            type = Void.class;
        }

        return wrap(type,
                    val);
    }
}
