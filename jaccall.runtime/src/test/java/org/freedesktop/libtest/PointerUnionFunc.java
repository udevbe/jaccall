package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnionFunc extends PointerFunc<UnionFunc> implements UnionFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(TestUnion.FFI_TYPE,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_SINT32,
                                                      JNI.FFI_TYPE_FLOAT);

    PointerUnionFunc(final long address) {
        super(UnionFunc.class,
              address);
    }

    @Nonnull
    public static Pointer<UnionFunc> nref(@Nonnull final UnionFunc function) {
        if (function instanceof PointerUnionFunc) {
            return (PointerUnionFunc) function;
        }
        return new UnionFunc_Jaccall_J(function);
    }
}
