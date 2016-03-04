package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Nonnull;

public abstract class PointerReadGlobalVarFunc extends PointerFunc<ReadGlobalVarFunc> implements ReadGlobalVarFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT32);

    PointerReadGlobalVarFunc(final long address) {
        super(ReadGlobalVarFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<ReadGlobalVarFunc> nref(@Nonnull ReadGlobalVarFunc func) {
        if (func instanceof PointerReadGlobalVarFunc) {
            return (PointerReadGlobalVarFunc) func;
        }

        return new ReadGlobalVarFunc_Jaccall_J(func);
    }
}
