package org.freedesktop.libtest;


import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class LongFunc_PointerFactory implements PointerFactory<PointerLongFunc> {
    @Override
    public PointerLongFunc create(final Type type,
                                  final long address,
                                  final boolean autoFree) {
        return new LongFunc_Jaccall_C(address);
    }
}
