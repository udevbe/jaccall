package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnsignedCharFunc extends PointerFunc<UnsignedCharFunc> implements UnsignedCharFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_UINT8,
                                                      JNI.FFI_TYPE_UINT8);

    PointerUnsignedCharFunc(final long address) {
        super(UnsignedCharFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<UnsignedCharFunc> nref(@Nonnull final UnsignedCharFunc function) {
        if (function instanceof PointerUnsignedCharFunc) {
            return (PointerUnsignedCharFunc) function;
        }
        return new UnsignedCharFunc_Jaccall_J(function);
    }
}