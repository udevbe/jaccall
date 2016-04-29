package org.freedesktop.libtest;

import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class UnionFunc2_PointerFactory implements PointerFactory<PointerUnionFunc2> {


    @Override
    public PointerUnionFunc2 create(final Type type,
                                   final long address,
                                    final boolean autoFree) {
        return new UnionFunc2_Jaccall_C(address);
    }
}
