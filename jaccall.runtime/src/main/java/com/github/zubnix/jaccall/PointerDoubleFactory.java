package com.github.zubnix.jaccall;


import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class PointerDoubleFactory implements PointerFactory<PointerDouble> {
    @Override
    public PointerDouble create(final Type type,
                                final long address,
                                final ByteBuffer buffer) {
        return new PointerDouble(address,
                                 buffer);
    }
}
