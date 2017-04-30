package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class ShortFunc_Jaccall_J extends PointerShortFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(ShortFunc.class,
                                                              "invoke",
                                                              "(S)S");
    private final ShortFunc function;

    public ShortFunc_Jaccall_J(final ShortFunc function) {
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
