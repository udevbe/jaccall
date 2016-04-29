package org.freedesktop.libtest;


import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class ReadGlobalVarFunc_PointerFactory implements PointerFactory<PointerReadGlobalVarFunc> {

    @Override
    public PointerReadGlobalVarFunc create(final Type type,
                                           final long address,
                                           final boolean autoFree) {
        return new ReadGlobalVarFunc_Jaccall_C(address);
    }
}
