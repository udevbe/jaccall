package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class ShortFunc_Jaccall_J extends PointerShortFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(ShortFunc.class,
                                                              "$",
                                                              "(S)S");
    private final ShortFunc function;

    public ShortFunc_Jaccall_J(final ShortFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID),
              ByteBuffer.allocate(0));
        this.function = function;
    }


    @Override
    public short $(final short value) {
        return this.function.$(value);
    }
}
