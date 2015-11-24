package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class UnsignedLongFunc_Jaccall_J extends PointerUnsignedLongFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.UnsignedLongFunc.class,
                                                              "$",
                                                              "(J)J");
    private final Testing.UnsignedLongFunc function;

    public UnsignedLongFunc_Jaccall_J(final Testing.UnsignedLongFunc function) {
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
