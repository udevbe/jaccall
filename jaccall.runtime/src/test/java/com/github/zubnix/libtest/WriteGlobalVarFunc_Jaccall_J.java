package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;

import java.nio.ByteBuffer;

final class WriteGlobalVarFunc_Jaccall_J extends PointerWriteGlobalVarFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(WriteGlobalVarFunc.class,
                                                              "$",
                                                              "()I");
    private final WriteGlobalVarFunc function;

    WriteGlobalVarFunc_Jaccall_J(final WriteGlobalVarFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID),
              ByteBuffer.allocate(0));
        this.function = function;
    }

    @Override
    public void $(final int var) {
        this.function.$(var);
    }
}
