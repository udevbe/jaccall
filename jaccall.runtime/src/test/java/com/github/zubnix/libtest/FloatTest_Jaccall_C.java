package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class FloatTest_Jaccall_C extends PointerFloatTest {

    static {
        JNI.linkFuncPtr(FloatTest_Jaccall_C.class,
                        "_$",
                        2,
                        "(JF)F",
                        FFI_CIF);
    }

    FloatTest_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public float $(final float value) {
        return _$(this.address,
                  value);
    }

    private static native float _$(final long address,
                                   final float value);
}
