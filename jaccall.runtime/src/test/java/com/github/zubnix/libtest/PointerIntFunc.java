package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerIntFunc extends PointerFunc<PointerIntFunc> implements IntFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT32,
                                                      JNI.FFI_TYPE_SINT32);

    PointerIntFunc(final long address) {
        super(PointerIntFunc.class,
              address);
    }

    @Nonnull
    public static PointerIntFunc wrapFunc(final long address) {
        return new IntFunc_Jaccall_C(address);
    }

    @Nonnull
    public static PointerIntFunc nref(@Nonnull final IntFunc function) {
        if (function instanceof PointerIntFunc) {
            return (PointerIntFunc) function;
        }
        return new IntFunc_Jaccall_J(function);
    }
}
