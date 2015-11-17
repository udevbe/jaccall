package com.github.zubnix.jaccall.runtime;

import com.github.zubnix.jaccall.runtime.api.Pointer;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class PointerStruct extends Pointer<StructType<?>>{
    public PointerStruct(@Nonnull final Type type,
                         final long address,
                         @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    public StructType<?> dref() {
        return null;
    }

    @Override
    public StructType<?> dref(final int index) {
        return null;
    }
}
