package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerStructFunc extends PointerFunc<StructFunc> implements StructFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(TestStruct.FFI_TYPE,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_SINT8,
                                                      JNI.FFI_TYPE_UINT16,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_SINT64,
                                                      JNI.FFI_TYPE_FLOAT);

    PointerStructFunc(final long address) {
        super(StructFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<StructFunc> nref(@Nonnull final StructFunc function) {
        if (function instanceof PointerStructFunc) {
            return (PointerStructFunc) function;
        }
        return new StructFunc_Jaccall_J(function);
    }
}
