package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class LongLongFunc_Jaccall_C extends PointerLongLongFunc {

    static {
        JNI.linkFuncPtr(LongLongFunc_Jaccall_C.class,
                        "_$",
                        2,
                        "(JJ)J",
                        FFI_CIF);
    }

    LongLongFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public long $(final long value) {
        return _$(this.address,
                  value);
    }

    private static native long _$(final long address,
                                  final long value);
}