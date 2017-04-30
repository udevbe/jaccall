package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnionFunc_Jaccall_J extends PointerUnionFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(UnionFunc.class,
                                                              "invoke",
                                                              "(JIF)J");
    private final UnionFunc function;

    public UnionFunc_Jaccall_J(final UnionFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }


    @Override
    public long invoke(long tst,
                  int field0,
                  float field1) {
        return this.function.invoke(tst,
                               field0,
                               field1);
    }
}
