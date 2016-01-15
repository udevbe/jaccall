package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class DoubleTest_Jaccall_J extends PointerDoubleTest {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.DoubleTest.class,
                                                              "$",
                                                              "(D)D");
    private final Testing.DoubleTest function;

    public DoubleTest_Jaccall_J(final Testing.DoubleTest function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }


    @Override
    public double $(final double value) {
        return this.function.$(value);
    }
}
