package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class IntTest_Jaccall_J extends PointerIntTest {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.IntTest.class,
                                                              "$",
                                                              "(I)I");
    private final Testing.IntTest function;

    public IntTest_Jaccall_J(final Testing.IntTest function) {
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
