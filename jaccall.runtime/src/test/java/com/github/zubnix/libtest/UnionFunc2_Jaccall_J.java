package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnionFunc2_Jaccall_J extends PointerUnionFunc2 {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(Testing.UnionFunc2.class,
                                                              "$",
                                                              "(JI)J");
    private final Testing.UnionFunc2 function;

    public UnionFunc2_Jaccall_J(final Testing.UnionFunc2 function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }


    @Override
    public long $(long tst,
                  int field0) {
        return this.function.$(tst,
                               field0);
    }
}
