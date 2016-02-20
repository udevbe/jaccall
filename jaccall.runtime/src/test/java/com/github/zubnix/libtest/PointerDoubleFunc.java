package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerDoubleFunc extends PointerFunc<PointerDoubleFunc> implements DoubleFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_DOUBLE,
                                                      JNI.FFI_TYPE_DOUBLE);

    PointerDoubleFunc(final long address,
                      final ByteBuffer buffer) {
        super(PointerDoubleFunc.class,
              address,
              buffer);
    }

    @Nonnull
    public static PointerDoubleFunc nref(@Nonnull final DoubleFunc function) {
        if (function instanceof PointerDoubleFunc) {
            return (PointerDoubleFunc) function;
        }
        return new DoubleFunc_Jaccall_J(function);
    }
}
