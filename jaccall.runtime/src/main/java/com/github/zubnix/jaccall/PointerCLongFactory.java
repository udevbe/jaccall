package com.github.zubnix.jaccall;


import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class PointerCLongFactory implements PointerFactory<PointerCLong> {
    @Override
    public PointerCLong create(final Type type,
                               final long address,
                               final ByteBuffer buffer) {
        return new PointerCLong(address,
                                buffer);
    }
}
