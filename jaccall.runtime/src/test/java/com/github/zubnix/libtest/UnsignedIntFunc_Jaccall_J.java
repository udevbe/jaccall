package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class UnsignedIntFunc_Jaccall_J extends PointerUnsignedIntFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.UnsignedIntFunc.class,
                                                              "$",
                                                              "(I)I");
    private final Testing.UnsignedIntFunc function;

    public UnsignedIntFunc_Jaccall_J(final Testing.UnsignedIntFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }


    @Override
    public int $(final int value) {
        return this.function.$(value);
    }
}
