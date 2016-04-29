package org.freedesktop.libtest;


import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class FooFunc_PointerFactory implements PointerFactory<PointerFooFunc> {

    @Override
    public PointerFooFunc create(final Type type,
                                 final long address,
                                 final boolean autoFree) {
        return new FooFunc_Jaccall_C(address);
    }
}
