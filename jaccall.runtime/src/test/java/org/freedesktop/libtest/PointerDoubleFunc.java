package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerDoubleFunc extends PointerFunc<PointerDoubleFunc> implements DoubleFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_DOUBLE,
                                                      JNI.FFI_TYPE_DOUBLE);

    PointerDoubleFunc(final long address) {
        super(PointerDoubleFunc.class,
              address);
    }

    @Nonnull
    public static PointerDoubleFunc nref(@Nonnull final DoubleFunc function) {
        if (function instanceof PointerDoubleFunc) {
            return (PointerDoubleFunc) function;
        }
        return new DoubleFunc_Jaccall_J(function);
    }
}
