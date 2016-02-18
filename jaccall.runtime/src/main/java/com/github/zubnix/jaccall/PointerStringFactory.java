package com.github.zubnix.jaccall;


import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class PointerStringFactory implements PointerFactory<PointerString> {
    @Override
    public PointerString create(final Type type,
                                final long address,
                                final ByteBuffer buffer) {
        return new PointerString(address,
                                 buffer);
    }
}
