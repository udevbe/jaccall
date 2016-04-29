package org.freedesktop.libtest;


import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class LongLongFunc_PointerFactory implements PointerFactory<PointerLongLongFunc> {
    @Override
    public PointerLongLongFunc create(final Type type,
                                      final long address,
                                      final boolean autoFree) {
        return new LongLongFunc_Jaccall_C(address);
    }
}
