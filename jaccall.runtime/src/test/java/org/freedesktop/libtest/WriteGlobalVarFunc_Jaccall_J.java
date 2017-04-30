package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;

final class WriteGlobalVarFunc_Jaccall_J extends PointerWriteGlobalVarFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(WriteGlobalVarFunc.class,
                                                              "invoke",
                                                              "()I");
    private final WriteGlobalVarFunc function;

    WriteGlobalVarFunc_Jaccall_J(final WriteGlobalVarFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }

    @Override
    public void invoke(final int var) {
        this.function.invoke(var);
    }
}
