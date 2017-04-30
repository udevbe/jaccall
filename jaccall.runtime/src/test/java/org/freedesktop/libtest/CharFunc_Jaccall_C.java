package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class CharFunc_Jaccall_C extends PointerCharFunc {

    static {
        JNI.linkFuncPtr(CharFunc_Jaccall_C.class,
                        "_invoke",
                        2,
                        "(JB)B",
                        FFI_CIF);
    }

    CharFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public byte invoke(final byte value) {
        return _invoke(this.address,
                  value);
    }

    private static native byte _invoke(final long address,
                                  final byte value);
}
