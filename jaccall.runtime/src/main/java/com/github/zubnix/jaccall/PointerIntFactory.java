package com.github.zubnix.jaccall;


import java.lang.reflect.Type;

final class PointerIntFactory implements PointerFactory<PointerInt> {
    @Override
    public PointerInt create(final Type type,
                             final long address,
                             final boolean autoFree) {
        return new PointerInt(address,
                              autoFree);
    }
}
