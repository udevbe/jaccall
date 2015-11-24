package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerFloatFunc extends PointerFunc<PointerFloatFunc> implements Testing.FloatFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_FLOAT,
                                                      JNI.FFI_TYPE_FLOAT);

    PointerFloatFunc(final long address) {
        super(PointerFloatFunc.class,
              address);
    }

    @Nonnull
    public static PointerFloatFunc wrapFunc(final long address) {
        return new FloatFunc_Jaccall_C(address);
    }

    @Nonnull
    public static PointerFloatFunc nref(@Nonnull final Testing.FloatFunc function) {
        if (function instanceof PointerFloatFunc) {
            return (PointerFloatFunc) function;
        }
        return new FloatFunc_Jaccall_J(function);
    }
}
