package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class LongLongFunc_PointerFactory implements PointerFactory<PointerLongLongFunc> {
    @Override
    public PointerLongLongFunc create(final Type type,
                                      final long address,
                                      final boolean autoFree) {
        return new LongLongFunc_Jaccall_C(address);
    }
}
