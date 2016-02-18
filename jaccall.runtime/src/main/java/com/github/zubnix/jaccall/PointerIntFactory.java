package com.github.zubnix.jaccall;


import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class PointerIntFactory implements PointerFactory<PointerInt> {
    @Override
    public PointerInt create(final Type type,
                             final long address,
                             final ByteBuffer buffer) {
        return new PointerInt(address,
                              buffer);
    }
}
