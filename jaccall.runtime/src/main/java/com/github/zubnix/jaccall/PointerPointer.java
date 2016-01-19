package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;


final class PointerPointer<T> extends Pointer<Pointer<T>> {
    PointerPointer(@Nonnull final Type type,
                   final long address,
                   @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer,
              sizeof((Pointer) null));
    }

    @Override
    Pointer<T> dref(@Nonnull final ByteBuffer byteBuffer) {
        return dref(0,
                    byteBuffer);
    }

    @Override
    Pointer<T> dref(@Nonnegative final int index,
                    @Nonnull final ByteBuffer byteBuffer) {
        final long val;
        if (this.typeSize == 8) {
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

        final Type genericClass;
        if (this.type instanceof ParameterizedType) {
            genericClass = this.type;
        }
        else {
            final Class<?> classType = toClass(this.type);
            genericClass = classType.getGenericSuperclass();
        }

        final Type type;
        if (genericClass != null && genericClass instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) genericClass;
            type = parameterizedType.getActualTypeArguments()[0];
        }
        else {
            //untyped pointer
            type = Void.class;
        }

        return wrap(type,
                    val,
                    JNI.wrap(val,
                             Integer.MAX_VALUE));
    }

    @SafeVarargs
    @Override
    final void write(@Nonnull final ByteBuffer byteBuffer,
                     @Nonnull final Pointer<T>... val) {
        writei(byteBuffer,
               0,
               val);
    }

    @SafeVarargs
    @Override
    public final void writei(@Nonnull final ByteBuffer byteBuffer,
                             @Nonnegative final int index,
                             @Nonnull final Pointer<T>... val) {
        final long pointerSize = sizeof((Pointer) null);
        if (pointerSize == 8) {
            //64-bit
            final LongBuffer buffer = byteBuffer.asLongBuffer();
            buffer.clear();
            buffer.position(index);
            for (final Pointer<?> pointer : val) {
                buffer.put(pointer.cast(Long.class));
            }
        }
        else if (pointerSize == 4) {
            //32-bit
            final IntBuffer buffer = byteBuffer.asIntBuffer();
            buffer.clear();
            buffer.position(index);
            for (final Pointer<?> pointer : val) {
                buffer.put(pointer.cast(Integer.class));
            }
        }
    }
}
