package org.freedesktop.jaccall;


import java.lang.reflect.Type;

final class PointerStructFactory implements PointerFactory<PointerStruct> {
    @Override
    public PointerStruct create(final Type type,
                                final long address,
                                final boolean autoFree) {
        try {
            return new PointerStruct(type,
                                     address,
                                     autoFree);
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new Error(e);
        }
    }
}
