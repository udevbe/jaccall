package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerShortFunc extends PointerFunc<ShortFunc> implements ShortFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT16,
                                                      JNI.FFI_TYPE_SINT16);

    PointerShortFunc(final long address,
                     final ByteBuffer buffer) {
        super(ShortFunc.class,
              address,
              buffer);
    }

    @Nonnull
    public static Pointer<ShortFunc> nref(@Nonnull final ShortFunc function) {
        if (function instanceof PointerShortFunc) {
            return (PointerShortFunc) function;
        }
        return new ShortFunc_Jaccall_J(function);
    }
}
