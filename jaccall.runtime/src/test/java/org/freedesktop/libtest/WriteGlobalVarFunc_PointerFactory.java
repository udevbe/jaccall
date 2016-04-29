package org.freedesktop.libtest;


import org.freedesktop.jaccall.PointerFactory;

import javax.annotation.Generated;
import java.lang.reflect.Type;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
public final class WriteGlobalVarFunc_PointerFactory implements PointerFactory<PointerWriteGlobalVarFunc> {

    @Override
    public PointerWriteGlobalVarFunc create(final Type type,
                                            final long address,
                                            final boolean autoFree) {
        return new WriteGlobalVarFunc_Jaccall_C(address);
    }
}
