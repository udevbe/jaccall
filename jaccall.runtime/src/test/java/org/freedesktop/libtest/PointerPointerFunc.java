package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerPointerFunc extends org.freedesktop.jaccall.PointerFunc<PointerFunc> implements PointerFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_POINTER);

    PointerPointerFunc(final long address) {
        super(PointerFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<PointerFunc> nref(@Nonnull final PointerFunc function) {
        if (function instanceof PointerPointerFunc) {
            return (PointerPointerFunc) function;
        }
        return new PointerFunc_Jaccall_J(function);
    }
}
