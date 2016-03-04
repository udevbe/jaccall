package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class UnionFunc_PointerFactory implements PointerFactory<PointerUnionFunc> {

    @Override
    public PointerUnionFunc create(final Type type,
                                   final long address,
                                   final boolean autoFree) {
        return new UnionFunc_Jaccall_C(address);
    }
}
