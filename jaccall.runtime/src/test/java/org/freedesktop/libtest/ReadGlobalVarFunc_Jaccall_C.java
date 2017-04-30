package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

final class ReadGlobalVarFunc_Jaccall_C extends PointerReadGlobalVarFunc {

    static {
        JNI.linkFuncPtr(ReadGlobalVarFunc_Jaccall_C.class,
                        "_invoke",
                        1,
                        "(J)I",
                        FFI_CIF);
    }

    ReadGlobalVarFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public int invoke() {
        return _invoke(this.address);
    }

    private static native int _invoke(final long address);
}
