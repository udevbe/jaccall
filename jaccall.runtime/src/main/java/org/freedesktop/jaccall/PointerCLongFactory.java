package org.freedesktop.jaccall;


import java.lang.reflect.Type;

final class PointerCLongFactory implements PointerFactory<PointerCLong> {
    @Override
    public PointerCLong create(final Type type,
                               final long address,
                               final boolean autoFree) {
        return new PointerCLong(address,
                                autoFree);
    }
}
