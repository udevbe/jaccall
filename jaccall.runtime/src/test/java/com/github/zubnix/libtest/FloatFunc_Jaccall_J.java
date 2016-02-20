package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class FloatFunc_Jaccall_J extends PointerFloatFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(FloatFunc.class,
                                                              "$",
                                                              "(F)F");
    private final FloatFunc function;

    public FloatFunc_Jaccall_J(final FloatFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID),
              ByteBuffer.allocate(0));
        this.function = function;
    }

    @Override
    public float $(final float value) {
        return this.function.$(value);
    }
}
