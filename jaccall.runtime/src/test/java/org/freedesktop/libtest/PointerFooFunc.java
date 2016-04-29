package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
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
    public static PointerFooFunc nref(@Nonnull final FooFunc function) {
        if (function instanceof FooFunc_Jaccall_J) {
            return (PointerFooFunc) function;
        }
        return new FooFunc_Jaccall_J(function);
    }
}
