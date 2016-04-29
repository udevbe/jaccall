package org.freedesktop.jaccall;


import java.lang.reflect.Type;

final class PointerDoubleFactory implements PointerFactory<PointerDouble> {
    @Override
    public PointerDouble create(final Type type,
                                final long address,
                                final boolean autoFree) {
        return new PointerDouble(address,
                                 autoFree);
    }
}
