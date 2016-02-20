package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnionFunc_Jaccall_C extends PointerUnionFunc {

    static {
        JNI.linkFuncPtr(UnionFunc_Jaccall_C.class,
                        "_$",
                        4,
                        "(JJIF)J",
                        FFI_CIF);
    }

    UnionFunc_Jaccall_C(final long address,
                        final ByteBuffer buffer) {
        super(address,
              buffer);
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
