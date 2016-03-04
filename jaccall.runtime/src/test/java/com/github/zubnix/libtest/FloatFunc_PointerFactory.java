package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class FloatFunc_PointerFactory implements PointerFactory<PointerFloatFunc> {

    @Override
    public PointerFloatFunc create(final Type type,
                                   final long address,
                                   final boolean autoFree) {
        return new FloatFunc_Jaccall_C(address);
    }
}
