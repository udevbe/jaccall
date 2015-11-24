package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class FloatFunc_Jaccall_J extends PointerFloatFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.FloatFunc.class,
                                                              "$",
                                                              "(F)F");
    private final Testing.FloatFunc function;

    public FloatFunc_Jaccall_J(final Testing.FloatFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }


    @Override
    public float $(final float value) {
        return this.function.$(value);
    }
}
