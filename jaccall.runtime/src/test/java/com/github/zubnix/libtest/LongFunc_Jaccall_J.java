package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class LongFunc_Jaccall_J extends PointerLongFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(LongFunc.class,
                                                              "$",
                                                              "(J)J");
    private final LongFunc function;

    public LongFunc_Jaccall_J(final LongFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID),
              ByteBuffer.allocate(0));
        this.function = function;
    }

    @Override
    public long $(final long value) {
        return this.function.$(value);
    }
}
