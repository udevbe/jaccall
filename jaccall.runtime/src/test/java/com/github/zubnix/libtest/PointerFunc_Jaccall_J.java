package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class PointerFunc_Jaccall_J extends PointerPointerFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(PointerFunc.class,
                                                              "$",
                                                              "(J)J");
    private final PointerFunc function;

    public PointerFunc_Jaccall_J(final PointerFunc function) {
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
