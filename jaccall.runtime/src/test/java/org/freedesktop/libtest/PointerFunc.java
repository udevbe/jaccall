package org.freedesktop.libtest;

import org.freedesktop.jaccall.Functor;
import org.freedesktop.jaccall.Ptr;

/**
 * Created by zubzub on 2/19/16.
 */
@Functor
public interface PointerFunc {
    @Ptr
    long $(@Ptr long value);
}
