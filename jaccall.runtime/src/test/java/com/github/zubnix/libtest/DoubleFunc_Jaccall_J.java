package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class DoubleFunc_Jaccall_J extends PointerDoubleFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(DoubleFunc.class,
                                                              "$",
                                                              "(D)D");
    private final DoubleFunc function;

    public DoubleFunc_Jaccall_J(final DoubleFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID),
              ByteBuffer.allocate(0));
        this.function = function;
    }


    @Override
    public double $(final double value) {
        return this.function.$(value);
    }
}
