package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnsignedLongLongFunc extends PointerFunc<UnsignedLongLongFunc> implements UnsignedLongLongFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_UINT64,
                                                      JNI.FFI_TYPE_UINT64);

    PointerUnsignedLongLongFunc(final long address) {
        super(UnsignedLongLongFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<UnsignedLongLongFunc> nref(@Nonnull final UnsignedLongLongFunc function) {
        if (function instanceof PointerUnsignedLongLongFunc) {
            return (PointerUnsignedLongLongFunc) function;
        }
        return new UnsignedLongLongFunc_Jaccall_J(function);
    }
}
