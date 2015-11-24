package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerLongFunc extends PointerFunc<PointerLongFunc> implements Testing.LongFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SLONG,
                                                      JNI.FFI_TYPE_SLONG);

    PointerLongFunc(final long address) {
        super(PointerLongFunc.class,
              address);
    }

    @Nonnull
    public static PointerLongFunc wrapFunc(final long address) {
        return new LongFunc_Jaccall_C(address);
    }

    @Nonnull
    public static PointerLongFunc nref(@Nonnull final Testing.LongFunc function) {
        if (function instanceof PointerLongFunc) {
            return (PointerLongFunc) function;
        }
        return new LongFunc_Jaccall_J(function);
    }
}
