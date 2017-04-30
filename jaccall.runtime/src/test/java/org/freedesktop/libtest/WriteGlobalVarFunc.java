package org.freedesktop.libtest;

import org.freedesktop.jaccall.Functor;

@Functor
public interface WriteGlobalVarFunc {
    void invoke(int var);
}
