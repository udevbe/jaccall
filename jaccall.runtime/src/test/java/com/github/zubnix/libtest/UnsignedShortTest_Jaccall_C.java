package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class UnsignedShortTest_Jaccall_C extends PointerUnsignedShortTest {

    static {
        JNI.linkFuncPtr(UnsignedShortTest_Jaccall_C.class,
                        "_$",
                        2,
                        "(JS)S",
                        PointerUnsignedShortTest.FFI_CIF);
    }

    UnsignedShortTest_Jaccall_C(final long address) {
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
