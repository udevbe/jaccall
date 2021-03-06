package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class StructFunc2_Jaccall_J extends PointerStructFunc2 {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(StructFunc2.class,
                                                              "invoke",
                                                              "(JBSJJJF)J");
    private final StructFunc2 function;

    public StructFunc2_Jaccall_J(final StructFunc2 function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }

    @Override
    public long invoke(long tst,
                  byte field0,
                  short field1,
                  long field2,
                  long field3,
                  long embedded_field0,
                  float embedded_field1) {
        return this.function.invoke(tst,
                               field0,
                               field1,
                               field2,
                               field3,
                               embedded_field0,
                               embedded_field1);
    }
}
