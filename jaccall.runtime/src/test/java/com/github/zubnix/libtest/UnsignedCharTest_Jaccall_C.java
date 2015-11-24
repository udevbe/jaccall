package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class UnsignedCharTest_Jaccall_C extends PointerUnsignedCharTest {

    static {
        JNI.linkFuncPtr(TestFunc_Jaccall_C.class,
                        "_$",
                        2,
                        "(JB)B",
                        PointerUnsignedCharTest.FFI_CIF);
    }

    UnsignedCharTest_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public byte $(final byte value) {
        return _$(this.address,
                  value);
    }

    private static native byte _$(final long address,
                                  final byte value);
}
