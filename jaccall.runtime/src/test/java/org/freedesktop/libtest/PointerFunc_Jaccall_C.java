package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class PointerFunc_Jaccall_C extends PointerPointerFunc {

    static {
        JNI.linkFuncPtr(PointerFunc_Jaccall_C.class,
                        "_invoke",
                        2,
                        "(JJ)J",
                        FFI_CIF);
    }

    PointerFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public long invoke(final long value) {
        return _invoke(this.address,
                  value);
    }

    private static native long _invoke(final long address,
                                  final long value);
}
