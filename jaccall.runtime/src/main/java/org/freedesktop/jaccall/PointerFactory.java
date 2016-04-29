package org.freedesktop.jaccall;

import java.lang.reflect.Type;

public interface PointerFactory<T extends Pointer<?>> {
    T create(final Type type,
             long address,
             boolean autoFree);
}
