package com.github.zubnix.jaccall.runtime;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import static com.github.zubnix.jaccall.runtime.Size.sizeOf;

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
    protected StructType dref(@Nonnull final ByteBuffer byteBuffer) {
        try {
            final StructType structType = this.structClass.newInstance();
            structType.buffer(byteBuffer);
            return structType;
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected StructType dref(@Nonnegative final int index,
                              @Nonnull final ByteBuffer byteBuffer) {
        try {
            final StructType structType = this.structClass.newInstance();
            byteBuffer.position((int) (index * sizeOf(structType)));
            final ByteBuffer slice = byteBuffer.slice();
            structType.buffer(slice);
            return structType;
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
