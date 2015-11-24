package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerPointerFunc extends PointerFunc<PointerPointerFunc> implements Testing.PointerFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_POINTER);

    PointerPointerFunc(final long address) {
        super(PointerPointerFunc.class,
              address);
    }

    @Nonnull
    public static PointerPointerFunc wrapFunc(final long address) {
        return new PointerFunc_Jaccall_C(address);
    }

    @Nonnull
    public static PointerPointerFunc nref(@Nonnull final Testing.PointerFunc function) {
        if (function instanceof PointerPointerFunc) {
            return (PointerPointerFunc) function;
        }
        return new PointerFunc_Jaccall_J(function);
    }
}
