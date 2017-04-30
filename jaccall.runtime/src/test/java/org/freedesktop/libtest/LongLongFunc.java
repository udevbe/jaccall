package org.freedesktop.libtest;

import org.freedesktop.jaccall.Functor;
import org.freedesktop.jaccall.Lng;

/**
 * Created by zubzub on 2/19/16.
 */
@Functor
public interface LongLongFunc {
    @Lng
    long invoke(@Lng long value);
}
