package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerStructFunc2 extends PointerFunc<PointerStructFunc2> implements Testing.StructFunc2 {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER,
                                                      TestStruct.FFI_TYPE,
                                                      JNI.FFI_TYPE_SINT8,
                                                      JNI.FFI_TYPE_UINT16,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_SINT64,
                                                      JNI.FFI_TYPE_FLOAT);

    PointerStructFunc2(final long address) {
        super(PointerStructFunc2.class,
              address);
    }

    @Nonnull
    public static PointerStructFunc2 wrapFunc(final long address) {
        return new StructFunc2_Jaccall_C(address);
    }

    @Nonnull
    public static PointerStructFunc2 nref(@Nonnull final Testing.StructFunc2 function) {
        if (function instanceof PointerStructFunc2) {
            return (PointerStructFunc2) function;
        }
        return new StructFunc2_Jaccall_J(function);
    }


}
