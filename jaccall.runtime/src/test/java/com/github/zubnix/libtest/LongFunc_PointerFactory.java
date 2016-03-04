package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class LongFunc_PointerFactory implements PointerFactory<PointerLongFunc> {
    @Override
    public PointerLongFunc create(final Type type,
                                  final long address,
                                  final boolean autoFree) {
        return new LongFunc_Jaccall_C(address);
    }
}
