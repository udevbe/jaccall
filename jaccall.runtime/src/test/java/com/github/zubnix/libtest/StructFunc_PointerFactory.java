package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class StructFunc_PointerFactory implements PointerFactory<PointerStructFunc> {

    @Override
    public PointerStructFunc create(final Type type,
                                    final long address,
                                    final boolean autoFree) {
        return new StructFunc_Jaccall_C(address);
    }
}
