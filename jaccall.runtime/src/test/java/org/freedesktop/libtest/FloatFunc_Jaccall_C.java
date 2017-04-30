package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class FloatFunc_Jaccall_C extends PointerFloatFunc {

    static {
        JNI.linkFuncPtr(FloatFunc_Jaccall_C.class,
                        "_invoke",
                        2,
                        "(JF)F",
                        FFI_CIF);
    }

    FloatFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public float invoke(final float value) {
        return _invoke(this.address,
                  value);
    }

    private static native float _invoke(final long address,
                                   final float value);
}
