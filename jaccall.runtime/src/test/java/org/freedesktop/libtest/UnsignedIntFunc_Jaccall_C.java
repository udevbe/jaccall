package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnsignedIntFunc_Jaccall_C extends PointerUnsignedIntFunc {

    static {
        JNI.linkFuncPtr(UnsignedIntFunc_Jaccall_C.class,
                        "_$",
                        2,
                        "(JI)I",
                        FFI_CIF);
    }

    UnsignedIntFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public int $(final int value) {
        return _$(this.address,
                  value);
    }

    private static native int _$(final long address,
                                 final int value);
}