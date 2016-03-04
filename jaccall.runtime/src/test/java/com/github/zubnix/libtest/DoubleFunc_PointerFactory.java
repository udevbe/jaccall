package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class DoubleFunc_PointerFactory implements PointerFactory<PointerDoubleFunc> {

    @Override
    public PointerDoubleFunc create(final Type type,
                                    final long address,
                                    final boolean autoFree) {
        return new DoubleFunc_Jaccall_C(address);
    }
}
