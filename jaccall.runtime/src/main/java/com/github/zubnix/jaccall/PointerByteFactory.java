package com.github.zubnix.jaccall;

import java.lang.reflect.Type;

final class PointerByteFactory implements PointerFactory<PointerByte> {

    @Override
    public PointerByte create(final Type type,
                              final long address,
                              final boolean autoFree) {
        return new PointerByte(address,
                               autoFree);
    }
}
