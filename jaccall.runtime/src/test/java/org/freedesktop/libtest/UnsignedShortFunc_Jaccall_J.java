package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnsignedShortFunc_Jaccall_J extends PointerUnsignedShortFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(UnsignedShortFunc.class,
                                                              "invoke",
                                                              "(S)S");
    private final UnsignedShortFunc function;

    public UnsignedShortFunc_Jaccall_J(final UnsignedShortFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }


    @Override
    public short invoke(final short value) {
        return this.function.invoke(value);
    }
}
