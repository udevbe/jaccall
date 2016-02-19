package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class UnsignedCharFunc_PointerFactory implements PointerFactory<PointerUnsignedCharFunc> {

    @Override
    public PointerUnsignedCharFunc create(final Type type,
                                          final long address,
                                          final ByteBuffer buffer) {
        return new UnsignedCharFunc_Jaccall_C(address);
    }
}