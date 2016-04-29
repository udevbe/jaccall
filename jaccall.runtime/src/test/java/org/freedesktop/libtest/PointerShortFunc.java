package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerShortFunc extends PointerFunc<ShortFunc> implements ShortFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT16,
                                                      JNI.FFI_TYPE_SINT16);

    PointerShortFunc(final long address) {
        super(ShortFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<ShortFunc> nref(@Nonnull final ShortFunc function) {
        if (function instanceof PointerShortFunc) {
            return (PointerShortFunc) function;
        }
        return new ShortFunc_Jaccall_J(function);
    }
}
