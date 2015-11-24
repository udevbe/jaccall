package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Nonnull;

public abstract class PointerStructFunc extends PointerFunc<PointerStructFunc> implements Testing.StructFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(TestStruct.FFI_TYPE,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_SINT8,
                                                      JNI.FFI_TYPE_UINT16,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_SINT64,
                                                      JNI.FFI_TYPE_FLOAT);

    PointerStructFunc(final long address) {
        super(PointerStructFunc.class,
              address);
    }

    @Nonnull
    public static PointerStructFunc wrapFunc(final long address) {
        return new StructFunc_Jaccall_C(address);
    }

    @Nonnull
    public static PointerStructFunc nref(@Nonnull final Testing.StructFunc function) {
        if (function instanceof PointerStructFunc) {
            return (PointerStructFunc) function;
        }
        return new StructFunc_Jaccall_J(function);
    }


}
