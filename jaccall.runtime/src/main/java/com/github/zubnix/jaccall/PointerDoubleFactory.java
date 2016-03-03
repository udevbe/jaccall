package com.github.zubnix.jaccall;


import java.lang.reflect.Type;

final class PointerDoubleFactory implements PointerFactory<PointerDouble> {
    @Override
    public PointerDouble create(final Type type,
                                final long address) {
        return new PointerDouble(address);
    }
}
