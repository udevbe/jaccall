package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class CharTest_Jaccall_C extends PointerCharTest {

    static {
        JNI.linkFuncPtr(TestFunc_Jaccall_C.class,
                        "_$",
                        2,
                        "(JB)B",
                        PointerCharTest.FFI_CIF);
    }

    CharTest_Jaccall_C(final long address) {
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
