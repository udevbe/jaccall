package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class IntFunc_Jaccall_J extends PointerIntFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(IntFunc.class,
                                                              "$",
                                                              "(I)I");
    private final IntFunc function;

    public IntFunc_Jaccall_J(final IntFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }


    @Override
    public int $(final int value) {
        return this.function.$(value);
    }
}
