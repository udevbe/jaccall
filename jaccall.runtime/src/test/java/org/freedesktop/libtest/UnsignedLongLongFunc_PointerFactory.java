package org.freedesktop.libtest;


import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class UnsignedLongLongFunc_PointerFactory implements PointerFactory<PointerUnsignedLongLongFunc> {

    @Override
    public PointerUnsignedLongLongFunc create(final Type type,
                                              final long address,
                                              final boolean autoFree) {
        return new UnsignedLongLongFunc_Jaccall_C(address);
    }
}
