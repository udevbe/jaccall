package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
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
