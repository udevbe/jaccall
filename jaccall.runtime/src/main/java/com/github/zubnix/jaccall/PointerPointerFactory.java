package com.github.zubnix.jaccall;

import java.lang.reflect.Type;


final class PointerPointerFactory implements PointerFactory<PointerPointer<?>> {
    @Override
    public PointerPointer<?> create(final Type type,
                                    final long address,
                                    final boolean autoFree) {
        return new PointerPointer<>(type,
                                    address,
                                    autoFree);
    }
}
