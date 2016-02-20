package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnsignedShortFunc extends PointerFunc<UnsignedShortFunc> implements UnsignedShortFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_UINT16,
                                                      JNI.FFI_TYPE_UINT16);

    PointerUnsignedShortFunc(final long address,
                             final ByteBuffer buffer) {
        super(UnsignedShortFunc.class,
              address,
              buffer);
    }

    @Nonnull
    public static Pointer<UnsignedShortFunc> nref(@Nonnull final UnsignedShortFunc function) {
        if (function instanceof UnsignedShortFunc_Jaccall_J) {
            return (PointerUnsignedShortFunc) function;
        }
        return new UnsignedShortFunc_Jaccall_J(function);
    }
}
