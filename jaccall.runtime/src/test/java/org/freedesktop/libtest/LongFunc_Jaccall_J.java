package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class LongFunc_Jaccall_J extends PointerLongFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(LongFunc.class,
                                                              "$",
                                                              "(J)J");
    private final LongFunc function;

    public LongFunc_Jaccall_J(final LongFunc function) {
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
