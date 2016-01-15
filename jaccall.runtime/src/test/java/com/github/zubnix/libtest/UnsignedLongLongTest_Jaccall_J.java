package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class UnsignedLongLongTest_Jaccall_J extends PointerUnsignedLongLongTest {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.UnsignedLongLongTest.class,
                                                              "$",
                                                              "(J)J");
    private final Testing.UnsignedLongLongTest function;

    public UnsignedLongLongTest_Jaccall_J(final Testing.UnsignedLongLongTest function) {
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
