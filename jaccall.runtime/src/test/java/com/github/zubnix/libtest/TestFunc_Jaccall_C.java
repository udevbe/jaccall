package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class TestFunc_Jaccall_C extends PointerTestFunc {

    static {
        JNI.linkFuncPtr(TestFunc_Jaccall_C.class,
                        "_$",
                        4,
                        "(JJIJ)B",
                        FFI_CIF);
    }

    TestFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public byte $(final long arg0, final int arg1, final long arg2) {
        return _$(this.address, arg0, arg1, arg2);
    }

    private static native byte _$(long address, long arg0, int arg1, long arg2);
}
