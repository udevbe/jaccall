package com.github.zubnix.jaccall;


import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class PointerVoidFactory implements PointerFactory<PointerVoid> {
    @Override
    public PointerVoid create(final Type type,
                              final long address,
                              final ByteBuffer buffer) {
        return new PointerVoid(address,
                               buffer);
    }
}
