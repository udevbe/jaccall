package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnionFunc_Jaccall_J extends PointerUnionFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(UnionFunc.class,
                                                              "$",
                                                              "(JIF)J");
    private final UnionFunc function;

    public UnionFunc_Jaccall_J(final UnionFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID),
              ByteBuffer.allocate(0));
        this.function = function;
    }


    @Override
    public long $(long tst,
                  int field0,
                  float field1) {
        return this.function.$(tst,
                               field0,
                               field1);
    }
}
