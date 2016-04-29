package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;

final class ReadGlobalVarFunc_Jaccall_J extends PointerReadGlobalVarFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(ReadGlobalVarFunc.class,
                                                              "$",
                                                              "()I");
    private final ReadGlobalVarFunc function;

    ReadGlobalVarFunc_Jaccall_J(final ReadGlobalVarFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID));
        this.function = function;
    }

    @Override
    public int $() {
        return this.function.$();
    }
}
