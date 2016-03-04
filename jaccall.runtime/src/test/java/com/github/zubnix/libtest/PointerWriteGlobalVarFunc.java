package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Nonnull;

public abstract class PointerWriteGlobalVarFunc extends PointerFunc<WriteGlobalVarFunc> implements WriteGlobalVarFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,
                                                      JNI.FFI_TYPE_SINT32);

    PointerWriteGlobalVarFunc(final long address) {
        super(WriteGlobalVarFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<WriteGlobalVarFunc> nref(@Nonnull final WriteGlobalVarFunc func) {
        if (func instanceof PointerWriteGlobalVarFunc) {
            return (PointerWriteGlobalVarFunc) func;
        }

        return new WriteGlobalVarFunc_Jaccall_J(func);
    }
}
