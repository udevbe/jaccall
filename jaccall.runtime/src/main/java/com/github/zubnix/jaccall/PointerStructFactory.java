package com.github.zubnix.jaccall;


import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class PointerStructFactory implements PointerFactory<PointerStruct> {
    @Override
    public PointerStruct create(final Type type,
                                final long address,
                                final ByteBuffer buffer) {
        try {
            return new PointerStruct(type,
                                     address,
                                     buffer);
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new Error(e);
        }
    }
}
