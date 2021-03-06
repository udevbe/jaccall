package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class ShortFunc_Jaccall_C extends PointerShortFunc {

    static {
        JNI.linkFuncPtr(ShortFunc_Jaccall_C.class,
                        "_invoke",
                        2,
                        "(JS)S",
                        FFI_CIF);
    }

    ShortFunc_Jaccall_C(final long address) {
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
