package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnsignedCharFunc_Jaccall_J extends PointerUnsignedCharFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(UnsignedCharFunc.class,
                                                              "invoke",
                                                              "(B)B");
    private final UnsignedCharFunc function;

    public UnsignedCharFunc_Jaccall_J(final UnsignedCharFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }


    @Override
    public byte invoke(final byte value) {
        return this.function.invoke(value);
    }
}
