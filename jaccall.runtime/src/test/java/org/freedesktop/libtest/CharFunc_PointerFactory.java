package org.freedesktop.libtest;

import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class CharFunc_PointerFactory implements PointerFactory<PointerCharFunc> {
    @Override
    public PointerCharFunc create(final Type type,
                                  final long address,
                                  final boolean autoFree) {
        return new CharFunc_Jaccall_C(address);
    }
}
