package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class UnsignedLongLongFunc_PointerFactory implements PointerFactory<PointerUnsignedLongLongFunc> {

    @Override
    public PointerUnsignedLongLongFunc create(final Type type,
                                              final long address,
                                              final boolean autoFree) {
        return new UnsignedLongLongFunc_Jaccall_C(address);
    }
}
