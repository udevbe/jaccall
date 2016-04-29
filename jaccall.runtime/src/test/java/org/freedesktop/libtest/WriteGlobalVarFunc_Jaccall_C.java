package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

final class WriteGlobalVarFunc_Jaccall_C extends PointerWriteGlobalVarFunc {

    static {
        JNI.linkFuncPtr(WriteGlobalVarFunc_Jaccall_C.class,
                        "_$",
                        2,
                        "(JI)V",
                        FFI_CIF);
    }

    WriteGlobalVarFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public void $(final int var) {
        _$(this.address,
           var);
    }

    private static native void _$(final long address,
                                  int var);
}
