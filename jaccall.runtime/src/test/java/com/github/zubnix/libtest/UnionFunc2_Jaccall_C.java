package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnionFunc2_Jaccall_C extends PointerUnionFunc2 {

    static {
        JNI.linkFuncPtr(UnionFunc2_Jaccall_C.class,
                        "_$",
                        3,
                        "(JJI)J",
                        FFI_CIF);
    }

    UnionFunc2_Jaccall_C(final long address,
                         final ByteBuffer buffer) {
        super(address,
              buffer);
    }

    @Override
    public long $(long tst,
                  int field0) {
        return _$(this.address,
                  tst,
                  field0);
    }

    private static native long _$(long address,
                                  long tst,
                                  int field0);
}
