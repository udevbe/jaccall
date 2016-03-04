package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnsignedLongFunc extends PointerFunc<UnsignedLongFunc> implements UnsignedLongFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_ULONG,
                                                      JNI.FFI_TYPE_ULONG);

    PointerUnsignedLongFunc(final long address) {
        super(UnsignedLongFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<UnsignedLongFunc> nref(@Nonnull final UnsignedLongFunc function) {
        if (function instanceof PointerUnsignedLongFunc) {
            return (PointerUnsignedLongFunc) function;
        }
        return new UnsignedLongFunc_Jaccall_J(function);
    }
}
