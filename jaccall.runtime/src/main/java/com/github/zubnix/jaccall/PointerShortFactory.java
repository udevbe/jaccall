package com.github.zubnix.jaccall;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class PointerShortFactory implements PointerFactory<PointerShort> {
    @Override
    public PointerShort create(final Type type,
                               final long address,
                               final ByteBuffer buffer) {
        return new PointerShort(address,
                                buffer);
    }
}
