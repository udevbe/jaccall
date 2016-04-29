package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerStructFunc2 extends PointerFunc<StructFunc2> implements StructFunc2 {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER,
                                                      TestStruct.FFI_TYPE,
                                                      JNI.FFI_TYPE_SINT8,
                                                      JNI.FFI_TYPE_UINT16,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_SINT64,
                                                      JNI.FFI_TYPE_FLOAT);

    PointerStructFunc2(final long address) {
        super(StructFunc2.class,
              address);
    }

    @Nonnull
    public static Pointer<StructFunc2> nref(@Nonnull final StructFunc2 function) {
        if (function instanceof PointerStructFunc2) {
            return (PointerStructFunc2) function;
        }
        return new StructFunc2_Jaccall_J(function);
    }


}
