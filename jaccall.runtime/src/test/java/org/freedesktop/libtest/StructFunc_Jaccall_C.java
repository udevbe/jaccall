package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class StructFunc_Jaccall_C extends PointerStructFunc {

    static {
        JNI.linkFuncPtr(StructFunc_Jaccall_C.class,
                        "_invoke",
                        8,
                        "(JJBSJJJF)J",
                        FFI_CIF);
    }

    StructFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public long invoke(long tst,
                  byte field0,
                  short field1,
                  long field2,
                  long field3,
                  long embedded_field0,
                  float embedded_field1) {
        return _invoke(this.address,
                  tst,
                  field0,
                  field1,
                  field2,
                  field3,
                  embedded_field0,
                  embedded_field1);
    }

    private static native long _invoke(final long address,
                                  long tst,
                                  byte field0,
                                  short field1,
                                  long field2,
                                  long field3,
                                  long embedded_field0,
                                  float embedded_field1);
}
