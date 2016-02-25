package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;

import java.nio.ByteBuffer;

final class ReadGlobalVarFunc_Jaccall_J extends PointerReadGlobalVarFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(ShortFunc.class,
                                                              "$",
                                                              "()I");
    private final ReadGlobalVarFunc function;

    ReadGlobalVarFunc_Jaccall_J(final ReadGlobalVarFunc function) {
        super(JNI.ffi_closure(FFI_CIF,
                              function,
                              JNI_METHOD_ID),
              ByteBuffer.allocate(0));
        this.function = function;
    }

    @Override
    public int $() {
        return this.function.$();
    }
}
