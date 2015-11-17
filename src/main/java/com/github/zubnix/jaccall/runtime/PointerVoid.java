package com.github.zubnix.jaccall.runtime;

import com.github.zubnix.jaccall.runtime.api.Pointer;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class PointerVoid extends Pointer<Void> {
    public PointerVoid(final Type type,
                       final long address,
                       final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    public Void dref() {
        return null;
    }

    @Override
    public Void dref(final int index) {
        return null;
    }
}
