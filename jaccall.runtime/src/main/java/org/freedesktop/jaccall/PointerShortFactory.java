package org.freedesktop.jaccall;

import java.lang.reflect.Type;

final class PointerShortFactory implements PointerFactory<PointerShort> {
    @Override
    public PointerShort create(final Type type,
                               final long address,
                               final boolean autoFree) {
        return new PointerShort(address,
                                autoFree);
    }
}
