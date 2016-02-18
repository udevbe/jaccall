package com.github.zubnix.jaccall;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class PointerLongFactory implements PointerFactory<PointerLong> {
    @Override
    public PointerLong create(final Type type,
                              final long address,
                              final ByteBuffer buffer) {
        return new PointerLong(address,
                               buffer);
    }
}
