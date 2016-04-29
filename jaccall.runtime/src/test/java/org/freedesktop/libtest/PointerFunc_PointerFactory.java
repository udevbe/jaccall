package org.freedesktop.libtest;


import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class PointerFunc_PointerFactory implements PointerFactory<PointerPointerFunc> {

    @Override
    public PointerPointerFunc create(final Type type,
                                     final long address,
                                     final boolean autoFree) {
        return new PointerFunc_Jaccall_C(address);
    }
}
