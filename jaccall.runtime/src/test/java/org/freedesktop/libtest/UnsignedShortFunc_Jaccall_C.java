package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnsignedShortFunc_Jaccall_C extends PointerUnsignedShortFunc {

    static {
        JNI.linkFuncPtr(UnsignedShortFunc_Jaccall_C.class,
                        "_$",
                        2,
                        "(JS)S",
                        FFI_CIF);
    }

    UnsignedShortFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public short $(final short value) {
        return _$(this.address,
                  value);
    }

    private static native short _$(final long address,
                                   final short value);
}
