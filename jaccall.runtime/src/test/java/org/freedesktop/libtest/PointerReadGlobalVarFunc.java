package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

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
