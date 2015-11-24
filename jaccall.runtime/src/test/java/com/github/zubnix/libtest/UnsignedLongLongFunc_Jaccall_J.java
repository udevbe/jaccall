package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class UnsignedLongLongFunc_Jaccall_J extends PointerUnsignedLongLongFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.UnsignedLongLongFunc.class,
                                                              "$",
                                                              "(J)J");
    private final Testing.UnsignedLongLongFunc function;

    public UnsignedLongLongFunc_Jaccall_J(final Testing.UnsignedLongLongFunc function) {
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
