package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class LongLongFunc_Jaccall_J extends PointerLongLongFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(LongLongFunc.class,
                                                              "$",
                                                              "(J)J");
    private final LongLongFunc function;

    public LongLongFunc_Jaccall_J(final LongLongFunc function) {
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
