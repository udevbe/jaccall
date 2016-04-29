package org.freedesktop.libtest;


import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class UnsignedCharFunc_PointerFactory implements PointerFactory<PointerUnsignedCharFunc> {

    @Override
    public PointerUnsignedCharFunc create(final Type type,
                                          final long address,
                                          final boolean autoFree) {
        return new UnsignedCharFunc_Jaccall_C(address);
    }
}