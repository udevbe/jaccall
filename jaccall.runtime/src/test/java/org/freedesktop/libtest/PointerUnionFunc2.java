package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public abstract class PointerUnionFunc2 extends PointerFunc<UnionFunc2> implements UnionFunc2 {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER,
                                                      TestUnion.FFI_TYPE,
                                                      JNI.FFI_TYPE_SINT32);

    PointerUnionFunc2(final long address) {
        super(UnionFunc2.class,
              address);
    }

    @Nonnull
    public static Pointer<UnionFunc2> nref(@Nonnull final UnionFunc2 function) {
        if (function instanceof PointerUnionFunc2) {
            return (PointerUnionFunc2) function;
        }
        return new UnionFunc2_Jaccall_J(function);
    }
}
