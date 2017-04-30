package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnionFunc_Jaccall_C extends PointerUnionFunc {

    static {
        JNI.linkFuncPtr(UnionFunc_Jaccall_C.class,
                        "_invoke",
                        4,
                        "(JJIF)J",
                        FFI_CIF);
    }

    UnionFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public long invoke(long tst,
                  int field0,
                  float field1) {
        return _invoke(this.address,
                  tst,
                  field0,
                  field1);
    }

    private static native long _invoke(long address,
                                  long tst,
                                  int field0,
                                  float field1);
}
