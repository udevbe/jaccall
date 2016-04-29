package org.freedesktop.libtest;


import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class UnsignedShortFunc_PointerFactory implements PointerFactory<PointerUnsignedShortFunc> {

    @Override
    public PointerUnsignedShortFunc create(final Type type,
                                           final long address,
                                           final boolean autoFree) {
        return new UnsignedShortFunc_Jaccall_C(address);
    }
}
