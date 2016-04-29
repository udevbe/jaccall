package org.freedesktop.libtest;

import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class UnionFunc_PointerFactory implements PointerFactory<PointerUnionFunc> {

    @Override
    public PointerUnionFunc create(final Type type,
                                   final long address,
                                   final boolean autoFree) {
        return new UnionFunc_Jaccall_C(address);
    }
}
