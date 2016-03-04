package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class CharFunc_Jaccall_J extends PointerCharFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(CharFunc.class,
                                                              "$",
                                                              "(B)B");

    private final CharFunc function;

    public CharFunc_Jaccall_J(final CharFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }

    @Override
    public byte $(final byte value) {
        return this.function.$(value);
    }
}
