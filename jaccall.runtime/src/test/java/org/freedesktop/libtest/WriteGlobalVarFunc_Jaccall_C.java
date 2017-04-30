package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

final class WriteGlobalVarFunc_Jaccall_C extends PointerWriteGlobalVarFunc {

    static {
        JNI.linkFuncPtr(WriteGlobalVarFunc_Jaccall_C.class,
                        "_invoke",
                        2,
                        "(JI)V",
                        FFI_CIF);
    }

    WriteGlobalVarFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public void invoke(final int var) {
        _invoke(this.address,
           var);
    }

    private static native void _invoke(final long address,
                                  int var);
}
