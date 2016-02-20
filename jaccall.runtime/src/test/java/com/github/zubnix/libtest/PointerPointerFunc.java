package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerPointerFunc extends com.github.zubnix.jaccall.PointerFunc<PointerFunc> implements PointerFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_POINTER);

    PointerPointerFunc(final long address,
                       final ByteBuffer buffer) {
        super(PointerFunc.class,
              address,
              buffer);
    }

    @Nonnull
    public static Pointer<PointerFunc> nref(@Nonnull final com.github.zubnix.libtest.PointerFunc function) {
        if (function instanceof PointerPointerFunc) {
            return (PointerPointerFunc) function;
        }
        return new PointerFunc_Jaccall_J(function);
    }
}
