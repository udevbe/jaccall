package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class CharFunc_PointerFactory implements PointerFactory<PointerCharFunc> {
    @Override
    public PointerCharFunc create(final Type type,
                                  final long address,
                                  final ByteBuffer buffer) {
        return new CharFunc_Jaccall_C(address,
                                      buffer);
    }
}
