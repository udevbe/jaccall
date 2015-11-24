package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class StructFunc_Jaccall_C extends PointerStructFunc {

    static {
        JNI.linkFuncPtr(StructFunc_Jaccall_C.class,
                        "_$",
                        8,
                        "(JJBSJJJF)J",
                        FFI_CIF);
    }

    StructFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public long $(long tst,
                  byte field0,
                  short field1,
                  long field2,
                  long field3,
                  long embedded_field0,
                  float embedded_field1) {
        return _$(this.address,
                  tst,
                  field0,
                  field1,
                  field2,
                  field3,
                  embedded_field0,
                  embedded_field1);
    }

    private static native short _$(final long address,
                                   long tst,
                                   byte field0,
                                   short field1,
                                   long field2,
                                   long field3,
                                   long embedded_field0,
                                   float embedded_field1);
}
