package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnsignedShortFunc extends PointerFunc<UnsignedShortFunc> implements UnsignedShortFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_UINT16,
                                                      JNI.FFI_TYPE_UINT16);

    PointerUnsignedShortFunc(final long address) {
        super(UnsignedShortFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<UnsignedShortFunc> nref(@Nonnull final UnsignedShortFunc function) {
        if (function instanceof UnsignedShortFunc_Jaccall_J) {
            return (PointerUnsignedShortFunc) function;
        }
        return new UnsignedShortFunc_Jaccall_J(function);
    }
}
