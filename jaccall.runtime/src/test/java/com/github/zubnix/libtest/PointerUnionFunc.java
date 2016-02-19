package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnionFunc extends PointerFunc<PointerUnionFunc> implements UnionFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(TestUnion.FFI_TYPE,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_SINT32,
                                                      JNI.FFI_TYPE_FLOAT);

    PointerUnionFunc(final long address) {
        super(PointerUnionFunc.class,
              address);
    }

    @Nonnull
    public static PointerUnionFunc wrapFunc(final long address) {
        return new UnionFunc_Jaccall_C(address);
    }

    @Nonnull
    public static PointerUnionFunc nref(@Nonnull final UnionFunc function) {
        if (function instanceof PointerUnionFunc) {
            return (PointerUnionFunc) function;
        }
        return new UnionFunc_Jaccall_J(function);
    }
}
