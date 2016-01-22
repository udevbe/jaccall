package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerUnionFunc2 extends PointerFunc<PointerUnionFunc2> implements Testing.UnionFunc2 {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER,
                                                      TestUnion.FFI_TYPE,
                                                      JNI.FFI_TYPE_SINT32);

    PointerUnionFunc2(final long address) {
        super(PointerUnionFunc2.class,
              address);
    }

    @Nonnull
    public static PointerUnionFunc2 wrapFunc(final long address) {
        return new UnionFunc2_Jaccall_C(address);
    }

    @Nonnull
    public static PointerUnionFunc2 nref(@Nonnull final Testing.UnionFunc2 function) {
        if (function instanceof PointerUnionFunc2) {
            return (PointerUnionFunc2) function;
        }
        return new UnionFunc2_Jaccall_J(function);
    }
}
