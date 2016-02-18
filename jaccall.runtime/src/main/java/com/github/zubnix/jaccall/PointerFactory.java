package com.github.zubnix.jaccall;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public interface PointerFactory<T extends Pointer<?>> {
    T create(final Type type,
             long address,
             ByteBuffer buffer);
}
