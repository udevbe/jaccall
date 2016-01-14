package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class UnsignedIntTest_Jaccall_J extends PointerUnsignedIntTest {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.UnsignedIntTest.class,
                                                              "$",
                                                              "(I)I");
    private final Testing.UnsignedIntTest function;

    public UnsignedIntTest_Jaccall_J(final Testing.UnsignedIntTest function) {
        super(JNI.ffi_closure(PointerUnsignedIntTest.FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }


    @Override
    public int $(final int value) {
        return this.function.$(value);
    }
}
