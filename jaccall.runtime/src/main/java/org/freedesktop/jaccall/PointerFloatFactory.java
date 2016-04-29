package org.freedesktop.jaccall;

import java.lang.reflect.Type;

final class PointerFloatFactory implements PointerFactory<PointerFloat> {
    @Override
    public PointerFloat create(final Type type,
                               final long address,
                               final boolean autoFree) {
        return new PointerFloat(address,
                                autoFree);
    }
}
