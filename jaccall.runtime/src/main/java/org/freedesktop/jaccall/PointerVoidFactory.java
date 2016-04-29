package org.freedesktop.jaccall;


import java.lang.reflect.Type;

final class PointerVoidFactory implements PointerFactory<PointerVoid> {
    @Override
    public PointerVoid create(final Type type,
                              final long address,
                              final boolean autoFree) {
        return new PointerVoid(address,
                               autoFree);
    }
}
