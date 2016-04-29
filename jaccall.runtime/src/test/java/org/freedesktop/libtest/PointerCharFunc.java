package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerCharFunc extends PointerFunc<CharFunc> implements CharFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT8,
                                                      JNI.FFI_TYPE_SINT8);


    PointerCharFunc(final long address) {
        super(CharFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<CharFunc> nref(@Nonnull final CharFunc function) {
        if (function instanceof PointerCharFunc) {
            return (PointerCharFunc) function;
        }
        return new CharFunc_Jaccall_J(function);
    }


}
