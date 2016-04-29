package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerFloatFunc extends PointerFunc<PointerFloatFunc> implements FloatFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_FLOAT,
                                                      JNI.FFI_TYPE_FLOAT);

    PointerFloatFunc(final long address) {
        super(PointerFloatFunc.class,
              address);
    }

    @Nonnull
    public static PointerFloatFunc nref(@Nonnull final FloatFunc function) {
        if (function instanceof PointerFloatFunc) {
            return (PointerFloatFunc) function;
        }
        return new FloatFunc_Jaccall_J(function);
    }
}
