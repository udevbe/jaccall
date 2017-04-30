package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class IntFunc_Jaccall_J extends PointerIntFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(IntFunc.class,
                                                              "invoke",
                                                              "(I)I");
    private final IntFunc function;

    public IntFunc_Jaccall_J(final IntFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }


    @Override
    public int invoke(final int value) {
        return this.function.invoke(value);
    }
}
