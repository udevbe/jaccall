package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnsignedLongFunc_Jaccall_J extends PointerUnsignedLongFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(UnsignedLongFunc.class,
                                                              "$",
                                                              "(J)J");
    private final UnsignedLongFunc function;

    public UnsignedLongFunc_Jaccall_J(final UnsignedLongFunc function) {
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
