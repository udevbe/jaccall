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

            final StructType structType = this.structClass.newInstance();
            final int structSize = sizeof(structType);
            byteBuffer.position(index * structSize);
            final ByteBuffer slice = byteBuffer.slice()
                                               .order(ByteOrder.nativeOrder());
            slice.limit(structSize);
            structType.buffer(slice);
            return structType;
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void write(@Nonnull final ByteBuffer byteBuffer,
                         @Nonnull final StructType... val) {
        writei(byteBuffer,
               0,
               val);
    }

    @Override
    public void writei(@Nonnull final ByteBuffer byteBuffer,
                       @Nonnegative final int index,
                       final StructType... val) {
        if (val.length == 0) {
            return;
        }

        byteBuffer.clear();
        final long structTypeSize = sizeof(val[0]);
        byteBuffer.position((int) (index * structTypeSize));
        for (StructType structType : val) {
            structType.buffer()
                      .rewind();
            structType.buffer()
                      .limit((int) structTypeSize);
            byteBuffer.put(structType.buffer());
        }
    }
}
