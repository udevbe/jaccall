package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerIntFunc extends PointerFunc<PointerIntFunc> implements IntFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT32,
                                                      JNI.FFI_TYPE_SINT32);

    PointerIntFunc(final long address,
                   final ByteBuffer buffer) {
        super(PointerIntFunc.class,
              address,
              buffer);
    }

    @Nonnull
    public static PointerIntFunc nref(@Nonnull final IntFunc function) {
        if (function instanceof PointerIntFunc) {
            return (PointerIntFunc) function;
        }
        return new IntFunc_Jaccall_J(function);
    }
}
