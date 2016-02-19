package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerLongLongFunc extends PointerFunc<PointerLongLongFunc> implements LongLongFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT64,
                                                      JNI.FFI_TYPE_SINT64);

    PointerLongLongFunc(final long address) {
        super(PointerLongLongFunc.class,
              address);
    }

    @Nonnull
    public static PointerLongLongFunc wrapFunc(final long address) {
        return new LongLongFunc_Jaccall_C(address);
    }

    @Nonnull
    public static PointerLongLongFunc nref(@Nonnull final LongLongFunc function) {
        if (function instanceof PointerLongLongFunc) {
            return (PointerLongLongFunc) function;
        }
        return new LongLongFunc_Jaccall_J(function);
    }
}
