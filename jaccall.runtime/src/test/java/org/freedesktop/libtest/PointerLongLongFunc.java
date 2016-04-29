package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerLongLongFunc extends PointerFunc<LongLongFunc> implements LongLongFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT64,
                                                      JNI.FFI_TYPE_SINT64);

    PointerLongLongFunc(final long address) {
        super(LongLongFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<LongLongFunc> nref(@Nonnull final LongLongFunc function) {
        if (function instanceof PointerLongLongFunc) {
            return (PointerLongLongFunc) function;
        }
        return new LongLongFunc_Jaccall_J(function);
    }
}
