package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnsignedShortFunc_Jaccall_C extends PointerUnsignedShortFunc {

    static {
        JNI.linkFuncPtr(UnsignedShortFunc_Jaccall_C.class,
                        "_invoke",
                        2,
                        "(JS)S",
                        FFI_CIF);
    }

    UnsignedShortFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public short invoke(final short value) {
        return _invoke(this.address,
                  value);
    }

    private static native short _invoke(final long address,
                                   final short value);
}
