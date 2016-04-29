package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class DoubleFunc_Jaccall_C extends PointerDoubleFunc {

    static {
        JNI.linkFuncPtr(DoubleFunc_Jaccall_C.class,
                        "_$",
                        2,
                        "(JD)D",
                        FFI_CIF);
    }

    DoubleFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public double $(final double value) {
        return _$(this.address,
                  value);
    }

    private static native double _$(final long address,
                                    final double value);
}
