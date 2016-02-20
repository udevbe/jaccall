package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerFloatFunc extends PointerFunc<PointerFloatFunc> implements FloatFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_FLOAT,
                                                      JNI.FFI_TYPE_FLOAT);

    PointerFloatFunc(final long address,
                     final ByteBuffer buffer) {
        super(PointerFloatFunc.class,
              address,
              buffer);
    }

    @Nonnull
    public static PointerFloatFunc nref(@Nonnull final FloatFunc function) {
        if (function instanceof PointerFloatFunc) {
            return (PointerFloatFunc) function;
        }
        return new FloatFunc_Jaccall_J(function);
    }
}
