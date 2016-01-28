package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class ShortFunc_Jaccall_J extends PointerShortFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.ShortFunc.class,
                                                              "$",
                                                              "(S)S");
    private final Testing.ShortFunc function;

    public ShortFunc_Jaccall_J(final Testing.ShortFunc function) {
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
