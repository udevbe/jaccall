package org.freedesktop.jaccall;

import java.lang.reflect.Type;

final class PointerLongFactory implements PointerFactory<PointerLong> {
    @Override
    public PointerLong create(final Type type,
                              final long address,
                              final boolean autoFree) {
        return new PointerLong(address,
                               autoFree);
    }
}
