package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerLongLongFunc extends PointerFunc<LongLongFunc> implements LongLongFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT64,
                                                      JNI.FFI_TYPE_SINT64);

    PointerLongLongFunc(final long address,
                        final ByteBuffer buffer) {
        super(LongLongFunc.class,
              address,
              buffer);
    }

    @Nonnull
    public static Pointer<LongLongFunc> nref(@Nonnull final LongLongFunc function) {
        if (function instanceof PointerLongLongFunc) {
            return (PointerLongLongFunc) function;
        }
        return new LongLongFunc_Jaccall_J(function);
    }
}
