package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnsignedLongFunc extends PointerFunc<UnsignedLongFunc> implements UnsignedLongFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_ULONG,
                                                      JNI.FFI_TYPE_ULONG);

    PointerUnsignedLongFunc(final long address) {
        super(UnsignedLongFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<UnsignedLongFunc> nref(@Nonnull final UnsignedLongFunc function) {
        if (function instanceof PointerUnsignedLongFunc) {
            return (PointerUnsignedLongFunc) function;
        }
        return new UnsignedLongFunc_Jaccall_J(function);
    }
}
