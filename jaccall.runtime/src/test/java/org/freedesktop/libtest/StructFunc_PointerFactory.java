package org.freedesktop.libtest;

import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class StructFunc_PointerFactory implements PointerFactory<PointerStructFunc> {

    @Override
    public PointerStructFunc create(final Type type,
                                    final long address,
                                    final boolean autoFree) {
        return new StructFunc_Jaccall_C(address);
    }
}
