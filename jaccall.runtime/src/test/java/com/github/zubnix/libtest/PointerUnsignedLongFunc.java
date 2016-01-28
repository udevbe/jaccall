package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnsignedLongFunc extends PointerFunc<PointerUnsignedLongFunc> implements Testing.LongFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_ULONG,
                                                      JNI.FFI_TYPE_ULONG);

    PointerUnsignedLongFunc(final long address) {
        super(PointerUnsignedLongFunc.class,
              address);
    }

    @Nonnull
    public static PointerUnsignedLongFunc wrapFunc(final long address) {
        return new UnsignedLongFunc_Jaccall_C(address);
    }

    @Nonnull
    public static PointerUnsignedLongFunc nref(@Nonnull final Testing.UnsignedLongFunc function) {
        if (function instanceof PointerUnsignedLongFunc) {
            return (PointerUnsignedLongFunc) function;
        }
        return new UnsignedLongFunc_Jaccall_J(function);
    }
}
