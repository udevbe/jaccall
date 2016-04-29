package org.freedesktop.libtest;


import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class IntFunc_PointerFactory implements PointerFactory<PointerIntFunc> {

    @Override
    public PointerIntFunc create(final Type type,
                                 final long address,
                                 final boolean autoFree) {
        return new IntFunc_Jaccall_C(address);
    }
}
