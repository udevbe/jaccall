package org.freedesktop.libtest;

import org.freedesktop.jaccall.Functor;
import org.freedesktop.jaccall.Lng;
import org.freedesktop.jaccall.Unsigned;

/**
 * Created by zubzub on 2/19/16.
 */
@Functor
public interface UnsignedLongLongFunc {
    @Unsigned
    @Lng
    long invoke(@Unsigned @Lng long value);
}
