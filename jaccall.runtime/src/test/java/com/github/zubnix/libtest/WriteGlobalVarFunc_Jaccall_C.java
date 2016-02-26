package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import java.nio.ByteBuffer;

final class WriteGlobalVarFunc_Jaccall_C extends PointerWriteGlobalVarFunc {

    static {
        JNI.linkFuncPtr(WriteGlobalVarFunc_Jaccall_C.class,
                        "_$",
                        2,
                        "(JI)V",
                        FFI_CIF);
    }

    WriteGlobalVarFunc_Jaccall_C(final long address,
                                 final ByteBuffer buffer) {
        super(address,
              buffer);
    }

    @Override
    public void $(final int var) {
        _$(this.address,
           var);
    }

    private static native void _$(final long address,
                                  int var);
}
