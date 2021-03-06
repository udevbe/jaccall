package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerLongFunc extends PointerFunc<LongFunc> implements LongFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SLONG,
                                                      JNI.FFI_TYPE_SLONG);

    PointerLongFunc(final long address) {
        super(LongFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<LongFunc> nref(@Nonnull final LongFunc function) {
        if (function instanceof PointerLongFunc) {
            return (PointerLongFunc) function;
        }
        return new LongFunc_Jaccall_J(function);
    }
}
