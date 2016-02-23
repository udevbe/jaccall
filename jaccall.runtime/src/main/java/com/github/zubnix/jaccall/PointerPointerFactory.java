package com.github.zubnix.jaccall;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;


final class PointerPointerFactory implements PointerFactory<PointerPointer<?>> {
    @Override
    public PointerPointer<?> create(final Type type,
                                    final long address,
                                    final ByteBuffer buffer) {
        return new PointerPointer<>(type,
                                    address,
                                    buffer);
    }
}