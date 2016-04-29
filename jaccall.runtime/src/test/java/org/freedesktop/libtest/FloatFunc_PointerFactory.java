package org.freedesktop.libtest;


import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class FloatFunc_PointerFactory implements PointerFactory<PointerFloatFunc> {

    @Override
    public PointerFloatFunc create(final Type type,
                                   final long address,
                                   final boolean autoFree) {
        return new FloatFunc_Jaccall_C(address);
    }
}
