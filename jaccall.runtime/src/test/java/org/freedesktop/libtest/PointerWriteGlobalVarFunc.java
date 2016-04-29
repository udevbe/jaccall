package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

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
