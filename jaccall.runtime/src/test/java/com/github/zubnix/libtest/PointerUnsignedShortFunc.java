package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnsignedShortFunc extends PointerFunc<PointerUnsignedShortFunc> implements UnsignedShortFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_UINT16,
                                                      JNI.FFI_TYPE_UINT16);

    PointerUnsignedShortFunc(final long address) {
        super(PointerUnsignedShortFunc.class,
              address);
    }

    @Nonnull
    public static PointerUnsignedShortFunc nref(@Nonnull final UnsignedShortFunc function) {
        if (function instanceof UnsignedShortFunc_Jaccall_J) {
            return (PointerUnsignedShortFunc) function;
        }
        return new UnsignedShortFunc_Jaccall_J(function);
    }
}
