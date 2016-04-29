package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class LongLongFunc_Jaccall_J extends PointerLongLongFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(LongLongFunc.class,
                                                              "$",
                                                              "(J)J");
    private final LongLongFunc function;

    public LongLongFunc_Jaccall_J(final LongLongFunc function) {
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
