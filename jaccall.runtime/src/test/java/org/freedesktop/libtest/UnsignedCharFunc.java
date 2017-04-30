package org.freedesktop.libtest;

import org.freedesktop.jaccall.Functor;
import org.freedesktop.jaccall.Unsigned;

/**
 * Created by zubzub on 2/19/16.
 */
@Functor
public interface UnsignedCharFunc {
    @Unsigned
    byte invoke(@Unsigned byte value);
}
