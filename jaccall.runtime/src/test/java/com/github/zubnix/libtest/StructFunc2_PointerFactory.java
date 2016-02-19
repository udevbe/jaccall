package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class StructFunc2_PointerFactory implements PointerFactory<PointerStructFunc2> {

    @Override
    public PointerStructFunc2 create(final Type type,
                                     final long address,
                                     final ByteBuffer buffer) {
        return new StructFunc2_Jaccall_C(address);
    }
}
