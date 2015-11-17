package com.github.zubnix.jaccall.runtime;

import com.github.zubnix.jaccall.runtime.api.CLong;
import com.github.zubnix.jaccall.runtime.api.Pointer;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;


public class PointerCLong extends Pointer<CLong> {
    public PointerCLong(@Nonnull final Type type,
                        final long address,
                        @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    public CLong dref() {
        return null;
    }

    @Override
    public CLong dref(final int index) {
        return null;
    }
}
