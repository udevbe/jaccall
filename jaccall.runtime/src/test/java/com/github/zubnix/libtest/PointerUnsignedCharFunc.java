package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnsignedCharFunc extends PointerFunc<UnsignedCharFunc> implements UnsignedCharFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_UINT8,
                                                      JNI.FFI_TYPE_UINT8);

    PointerUnsignedCharFunc(final long address) {
        super(UnsignedCharFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<UnsignedCharFunc> nref(@Nonnull final UnsignedCharFunc function) {
        if (function instanceof PointerUnsignedCharFunc) {
            return (PointerUnsignedCharFunc) function;
        }
        return new UnsignedCharFunc_Jaccall_J(function);
    }
}