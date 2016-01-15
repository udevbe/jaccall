package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class CharTest_Jaccall_J extends PointerCharTest {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.CharTest.class,
                                                              "$",
                                                              "(B)B");
    private final Testing.CharTest function;

    public CharTest_Jaccall_J(final Testing.CharTest function) {
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
