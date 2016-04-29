package org.freedesktop.libtest;


import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class UnsignedLongFunc_PointerFactory implements PointerFactory<PointerUnsignedLongFunc> {

    @Override
    public PointerUnsignedLongFunc create(final Type type,
                                          final long address,
                                          final boolean autoFree) {
        return new UnsignedLongFunc_Jaccall_C(address);
    }
}
