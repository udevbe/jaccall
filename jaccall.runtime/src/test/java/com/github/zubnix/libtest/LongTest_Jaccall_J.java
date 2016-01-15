package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class LongTest_Jaccall_J extends PointerLongTest {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.LongTest.class,
                                                              "$",
                                                              "(J)J");
    private final Testing.LongTest function;

    public LongTest_Jaccall_J(final Testing.LongTest function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }

    @Override
    public long $(final long value) {
        return this.function.$(value);
    }
}
