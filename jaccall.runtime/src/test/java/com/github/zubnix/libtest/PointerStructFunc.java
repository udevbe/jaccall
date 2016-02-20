package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerStructFunc extends PointerFunc<StructFunc> implements StructFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(TestStruct.FFI_TYPE,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_SINT8,
                                                      JNI.FFI_TYPE_UINT16,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_SINT64,
                                                      JNI.FFI_TYPE_FLOAT);

    PointerStructFunc(final long address,
                      final ByteBuffer buffer) {
        super(StructFunc.class,
              address,
              buffer);
    }

    @Nonnull
    public static Pointer<StructFunc> nref(@Nonnull final StructFunc function) {
        if (function instanceof PointerStructFunc) {
            return (PointerStructFunc) function;
        }
        return new StructFunc_Jaccall_J(function);
    }
}
