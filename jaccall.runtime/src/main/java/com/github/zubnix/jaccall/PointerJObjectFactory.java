package com.github.zubnix.jaccall;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class PointerJObjectFactory implements PointerFactory<PointerJObject> {
    @Override
    public PointerJObject create(final Type type,
                                 final long address,
                                 final ByteBuffer buffer) {
        return new PointerJObject(address,
                                  buffer);
    }
}
