package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class LongFunc_Jaccall_J extends PointerLongFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.LongFunc.class,
                                                              "$",
                                                              "(J)J");
    private final Testing.LongFunc function;

    public LongFunc_Jaccall_J(final Testing.LongFunc function) {
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