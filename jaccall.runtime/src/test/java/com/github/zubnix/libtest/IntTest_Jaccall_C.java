package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class IntTest_Jaccall_C extends PointerIntTest {

    static {
        JNI.linkFuncPtr(IntTest_Jaccall_C.class,
                        "_$",
                        2,
                        "(JI)I",
                        PointerIntTest.FFI_CIF);
    }

    IntTest_Jaccall_C(final long address) {
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
