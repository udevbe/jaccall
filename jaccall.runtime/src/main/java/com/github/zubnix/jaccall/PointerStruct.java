package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.github.zubnix.jaccall.Size.sizeof;

final class PointerStruct extends Pointer<StructType> {

    private final Class<? extends StructType> structClass;
    private final int                         structSize;

    PointerStruct(@Nonnull final Type type,
                  final long address,
                  @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
        this.structClass = (Class<? extends StructType>) toClass(type);
        try {
            this.structSize = sizeof(this.structClass.newInstance());
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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
            //TODO it's probably ok to do this once and reuse the returned instance for the given index(?)
            final StructType structType = this.structClass.newInstance();
            byteBuffer.position(index * this.structSize);
            final ByteBuffer slice = byteBuffer.slice()
                                               .order(ByteOrder.nativeOrder());
            slice.limit(this.structSize);
            structType.buffer(slice);
            return structType;
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    @Override
    public Pointer<StructType> offset(final int offset) {
        return wrap(this.type,
                    this.address + (offset * this.structSize));
    }

    @Override
    void write(@Nonnull final ByteBuffer byteBuffer,
               @Nonnull final StructType... val) {
        writei(byteBuffer,
               0,
               val);
    }

    @Override
    void writei(@Nonnull final ByteBuffer byteBuffer,
                @Nonnegative final int index,
                @Nonnull final StructType... val) {
        if (val.length == 0) {
            return;
        }

        byteBuffer.clear();
        final long structTypeSize = sizeof(val[0]);
        byteBuffer.position((int) (index * structTypeSize));
        for (final StructType structType : val) {
            structType.buffer()
                      .rewind();
            byteBuffer.put(structType.buffer());
        }
    }
}
