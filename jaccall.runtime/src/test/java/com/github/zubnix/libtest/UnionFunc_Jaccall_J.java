package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class UnionFunc_Jaccall_J extends PointerUnionFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.UnionFunc.class,
                                                              "$",
                                                              "(JIF)J");
    private final Testing.UnionFunc function;

    public UnionFunc_Jaccall_J(final Testing.UnionFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
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
