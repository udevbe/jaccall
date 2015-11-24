package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerCharFunc extends PointerFunc<PointerCharFunc> implements Testing.CharFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT8,
                                                      JNI.FFI_TYPE_SINT8);

    PointerCharFunc(final long address) {
        super(PointerCharFunc.class,
              address);
    }

    @Nonnull
    public static PointerCharFunc wrapFunc(final long address) {
        return new CharFunc_Jaccall_C(address);
    }

    @Nonnull
    public static PointerCharFunc nref(@Nonnull final Testing.CharFunc function) {
        if (function instanceof PointerCharFunc) {
            return (PointerCharFunc) function;
        }
        return new CharFunc_Jaccall_J(function);
    }


}
