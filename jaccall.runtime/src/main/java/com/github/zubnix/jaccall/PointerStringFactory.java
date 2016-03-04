package com.github.zubnix.jaccall;


import java.lang.reflect.Type;

final class PointerStringFactory implements PointerFactory<PointerString> {
    @Override
    public PointerString create(final Type type,
                                final long address,
                                final boolean autoFree) {
        return new PointerString(address,
                                 autoFree);
    }
}
