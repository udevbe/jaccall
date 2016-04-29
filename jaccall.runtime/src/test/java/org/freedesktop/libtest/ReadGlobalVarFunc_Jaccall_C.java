package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

final class ReadGlobalVarFunc_Jaccall_C extends PointerReadGlobalVarFunc {

    static {
        JNI.linkFuncPtr(ReadGlobalVarFunc_Jaccall_C.class,
                        "_$",
                        1,
                        "(J)I",
                        FFI_CIF);
    }

    ReadGlobalVarFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public int $() {
        return _$(this.address);
    }

    private static native int _$(final long address);
}
