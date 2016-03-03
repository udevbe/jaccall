package com.github.zubnix.jaccall;


import java.lang.reflect.Type;

final class PointerCLongFactory implements PointerFactory<PointerCLong> {
    @Override
    public PointerCLong create(final Type type,
                               final long address) {
        return new PointerCLong(address);
    }
}
