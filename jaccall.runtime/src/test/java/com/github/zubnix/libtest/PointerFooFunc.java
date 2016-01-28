package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerFooFunc extends PointerFunc<PointerFooFunc> implements FooFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT8,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_UINT32,
                                                      TestStruct.FFI_TYPE);

    PointerFooFunc(final long address) {
        super(PointerFooFunc.class,
              address);
    }

    @Nonnull
    public static PointerFooFunc wrapFunc(final long address) {
        return new FooFunc_Jaccall_C(address);
    }

    @Nonnull
    public static PointerFooFunc nref(@Nonnull final FooFunc function) {
        if (function instanceof FooFunc_Jaccall_J) {
            return (PointerFooFunc) function;
        }
        return new FooFunc_Jaccall_J(function);
    }
}
