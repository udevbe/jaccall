package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.github.zubnix.jaccall.Size.sizeof;

final class PointerStruct extends Pointer<StructType> {

    private final Class<? extends StructType> structClass;

    PointerStruct(@Nonnull final Type type,
                  final long address,
                  @Nonnull final ByteBuffer byteBuffer) throws IllegalAccessException, InstantiationException {
        super(type,
              address,
              byteBuffer,
              sizeof(((Class<? extends StructType>) toClass(type)).newInstance()));
        this.structClass = (Class<? extends StructType>) toClass(type);
    }

    @Nonnull
    @Override
    public StructType dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public StructType dref(@Nonnegative final int index) {
        try {
            final StructType structType = this.structClass.newInstance();
            this.byteBuffer.position(index * this.typeSize);
            final ByteBuffer slice = this.byteBuffer.slice()
                                                    .order(ByteOrder.nativeOrder());
            slice.limit(this.typeSize);
            structType.buffer(slice);
            return structType;
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(@Nonnull final StructType val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final StructType val) {
        final ByteBuffer valBuffer = val.buffer();
        valBuffer.rewind();

        this.byteBuffer.clear();
        this.byteBuffer.position(index * this.typeSize);
        this.byteBuffer.put(valBuffer);
    }
}
