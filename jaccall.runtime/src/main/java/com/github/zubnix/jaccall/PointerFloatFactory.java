package com.github.zubnix.jaccall;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class PointerFloatFactory implements PointerFactory<PointerFloat> {
    @Override
    public PointerFloat create(final Type type,
                               final long address,
                               final ByteBuffer buffer) {
        return new PointerFloat(address,
                                buffer);
    }
}
