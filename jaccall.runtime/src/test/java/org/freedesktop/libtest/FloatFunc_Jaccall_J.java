package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class FloatFunc_Jaccall_J extends PointerFloatFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(FloatFunc.class,
                                                              "invoke",
                                                              "(F)F");
    private final FloatFunc function;

    public FloatFunc_Jaccall_J(final FloatFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }

    @Override
    public float invoke(final float value) {
        return this.function.invoke(value);
    }
}
