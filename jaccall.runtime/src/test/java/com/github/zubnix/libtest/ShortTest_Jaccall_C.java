package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class ShortTest_Jaccall_C extends PointerShortTest {

    static {
        JNI.linkFuncPtr(ShortTest_Jaccall_C.class,
                        "_$",
                        2,
                        "(JS)S",
                        PointerShortTest.FFI_CIF);
    }

    ShortTest_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public short $(final short value) {
        return _$(this.address,
                  value);
    }

    private static native byte _$(final long address,
                                  final short value);
}
