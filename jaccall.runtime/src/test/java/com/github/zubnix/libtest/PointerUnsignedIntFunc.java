package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerUnsignedIntFunc extends PointerFunc<PointerUnsignedIntFunc> implements Testing.UnsignedIntFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_UINT32,
                                                      JNI.FFI_TYPE_UINT32);

    PointerUnsignedIntFunc(final long address) {
        super(PointerUnsignedIntFunc.class,
              address);
    }

    @Nonnull
    public static PointerUnsignedIntFunc wrapFunc(final long address) {
        return new UnsignedIntFunc_Jaccall_C(address);
    }

    @Nonnull
    public static PointerUnsignedIntFunc nref(@Nonnull final Testing.UnsignedIntFunc function) {
        if (function instanceof PointerUnsignedIntFunc) {
            return (PointerUnsignedIntFunc) function;
        }
        return new UnsignedIntFunc_Jaccall_J(function);
    }
}
