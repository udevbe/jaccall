package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class UnsignedCharTest_Jaccall_J extends PointerUnsignedCharTest {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.UnsignedCharTest.class,
                                                              "$",
                                                              "(B)B");
    private final Testing.UnsignedCharTest function;

    public UnsignedCharTest_Jaccall_J(final Testing.UnsignedCharTest function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }


    @Override
    public byte $(final byte value) {
        return this.function.$(value);
    }
}
