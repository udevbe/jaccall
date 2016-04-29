package org.freedesktop.libtest;


import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class DoubleFunc_PointerFactory implements PointerFactory<PointerDoubleFunc> {

    @Override
    public PointerDoubleFunc create(final Type type,
                                    final long address,
                                    final boolean autoFree) {
        return new DoubleFunc_Jaccall_C(address);
    }
}
