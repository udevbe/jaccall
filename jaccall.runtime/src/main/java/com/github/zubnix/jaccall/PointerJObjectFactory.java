package com.github.zubnix.jaccall;

import java.lang.reflect.Type;

final class PointerJObjectFactory implements PointerFactory<PointerJObject> {
    @Override
    public PointerJObject create(final Type type,
                                 final long address,
                                 final boolean autoFree) {
        return new PointerJObject(address,
                                  autoFree);
    }
}
