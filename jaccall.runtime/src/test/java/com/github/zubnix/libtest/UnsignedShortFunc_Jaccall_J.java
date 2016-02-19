package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnsignedShortFunc_Jaccall_J extends PointerUnsignedShortFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(UnsignedShortFunc.class,
                                                              "$",
                                                              "(S)S");
    private final UnsignedShortFunc function;

    public UnsignedShortFunc_Jaccall_J(final UnsignedShortFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }


    @Override
    public short $(final short value) {
        return this.function.$(value);
    }
}
