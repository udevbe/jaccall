package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class LongLongFunc_Jaccall_J extends PointerLongLongFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.LongLongFunc.class,
                                                              "$",
                                                              "(J)J");
    private final Testing.LongLongFunc function;

    public LongLongFunc_Jaccall_J(final Testing.LongLongFunc function) {
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
