package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import java.nio.ByteBuffer;

final class ReadGlobalVarFunc_Jaccall_C extends PointerReadGlobalVarFunc {

    static {
        JNI.linkFuncPtr(ReadGlobalVarFunc_Jaccall_C.class,
                        "_$",
                        1,
                        "(J)I",
                        FFI_CIF);
    }

    ReadGlobalVarFunc_Jaccall_C(final long address,
                                final ByteBuffer buffer) {
        super(address,
              buffer);
    }

    @Override
    public int $() {
        return _$(this.address);
    }

    private static native int _$(final long address);
}
