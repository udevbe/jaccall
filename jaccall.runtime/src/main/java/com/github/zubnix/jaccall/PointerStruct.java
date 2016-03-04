package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;

import static com.github.zubnix.jaccall.Size.sizeof;

final class PointerStruct extends Pointer<StructType> {

    private final Class<? extends StructType> structClass;

    PointerStruct(@Nonnull final Type type,
                  final long address,
                  final boolean autoFree) throws IllegalAccessException, InstantiationException {
        super(type,
              address,
              autoFree,
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
            structType.address(this.address + (index * this.typeSize));
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
        final int offset = index * this.typeSize;
        JNI.writeStruct(this.address + offset,
                        val.address(),
                        this.typeSize);
    }
}
