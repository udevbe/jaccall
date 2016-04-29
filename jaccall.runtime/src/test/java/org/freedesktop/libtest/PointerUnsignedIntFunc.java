package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnsignedIntFunc extends PointerFunc<UnsignedIntFunc> implements UnsignedIntFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_UINT32,
                                                      JNI.FFI_TYPE_UINT32);

    PointerUnsignedIntFunc(final long address) {
        super(UnsignedIntFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<UnsignedIntFunc> nref(@Nonnull final UnsignedIntFunc function) {
        if (function instanceof PointerUnsignedIntFunc) {
            return (PointerUnsignedIntFunc) function;
        }
        return new UnsignedIntFunc_Jaccall_J(function);
    }
}
