package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnionFunc2_Jaccall_J extends PointerUnionFunc2 {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(UnionFunc2.class,
                                                              "invoke",
                                                              "(JI)J");
    private final UnionFunc2 function;

    public UnionFunc2_Jaccall_J(final UnionFunc2 function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }


    @Override
    public long invoke(long tst,
                  int field0) {
        return this.function.invoke(tst,
                               field0);
    }
}
