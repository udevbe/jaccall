package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import static com.github.zubnix.jaccall.Size.sizeOf;

final class PointerStruct extends Pointer<StructType> {

    private final Class<? extends StructType> structClass;

    PointerStruct(@Nonnull final Type type,
                  final long address,
                  @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
        this.structClass = (Class<? extends StructType>) toClass(type);
    }

    @Override
    StructType dref(@Nonnull final ByteBuffer byteBuffer) {
        return dref(0,
                    byteBuffer);
    }

    @Override
    StructType dref(@Nonnegative final int index,
                    @Nonnull final ByteBuffer byteBuffer) {
        try {
            final int structSize = Size.sizeOf(this.structClass);
            byteBuffer.position(index * structSize);
            final StructType structType = this.structClass.newInstance();
            final ByteBuffer slice = byteBuffer.slice();
            slice.limit(structSize);
            structType.buffer(slice);
            return structType;
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
