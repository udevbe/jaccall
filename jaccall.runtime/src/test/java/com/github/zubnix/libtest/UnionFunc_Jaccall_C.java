package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class UnionFunc_Jaccall_C extends PointerUnionFunc {

    static {
        JNI.linkFuncPtr(UnionFunc_Jaccall_C.class,
                        "_$",
                        4,
                        "(JJIF)J",
                        FFI_CIF);
    }

    UnionFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public long $(long tst,
                  int field0,
                  float field1) {
        return _$(this.address,
                  tst,
                  field0,
                  field1);
    }

    private static native long _$(long address,
                                  long tst,
                                  int field0,
                                  float field1);
}
