package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class UnsignedIntFunc_PointerFactory implements PointerFactory<PointerUnsignedIntFunc> {

    @Override
    public PointerUnsignedIntFunc create(final Type type,
                                         final long address,
                                         final boolean autoFree) {
        return new UnsignedIntFunc_Jaccall_C(address);
    }
}
