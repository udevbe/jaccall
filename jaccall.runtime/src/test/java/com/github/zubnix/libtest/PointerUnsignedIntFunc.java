package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnsignedIntFunc extends PointerFunc<UnsignedIntFunc> implements UnsignedIntFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_UINT32,
                                                      JNI.FFI_TYPE_UINT32);

    PointerUnsignedIntFunc(final long address,
                           final ByteBuffer buffer) {
        super(UnsignedIntFunc.class,
              address,
              buffer);
    }

    @Nonnull
    public static Pointer<UnsignedIntFunc> nref(@Nonnull final UnsignedIntFunc function) {
        if (function instanceof PointerUnsignedIntFunc) {
            return (PointerUnsignedIntFunc) function;
        }
        return new UnsignedIntFunc_Jaccall_J(function);
    }
}
