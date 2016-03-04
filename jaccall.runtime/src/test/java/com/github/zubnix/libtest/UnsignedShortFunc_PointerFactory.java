package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class UnsignedShortFunc_PointerFactory implements PointerFactory<PointerUnsignedShortFunc> {

    @Override
    public PointerUnsignedShortFunc create(final Type type,
                                           final long address,
                                           final boolean autoFree) {
        return new UnsignedShortFunc_Jaccall_C(address);
    }
}
