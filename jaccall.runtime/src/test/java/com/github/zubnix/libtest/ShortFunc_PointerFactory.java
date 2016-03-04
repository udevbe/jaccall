package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class ShortFunc_PointerFactory implements PointerFactory<PointerShortFunc> {

    @Override
    public PointerShortFunc create(final Type type,
                                   final long address,
                                   final boolean autoFree) {
        return new ShortFunc_Jaccall_C(address);
    }
}
