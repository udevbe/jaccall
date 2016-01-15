package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class LongLongTest_Jaccall_J extends PointerLongLongTest {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.LongLongTest.class,
                                                              "$",
                                                              "(J)J");
    private final Testing.LongLongTest function;

    public LongLongTest_Jaccall_J(final Testing.LongLongTest function) {
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
