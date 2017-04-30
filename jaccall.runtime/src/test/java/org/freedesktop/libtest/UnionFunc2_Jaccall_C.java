package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnionFunc2_Jaccall_C extends PointerUnionFunc2 {

    static {
        JNI.linkFuncPtr(UnionFunc2_Jaccall_C.class,
                        "_invoke",
                        3,
                        "(JJI)J",
                        FFI_CIF);
    }

    UnionFunc2_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public long invoke(long tst,
                  int field0) {
        return _invoke(this.address,
                  tst,
                  field0);
    }

    private static native long _invoke(long address,
                                  long tst,
                                  int field0);
}
