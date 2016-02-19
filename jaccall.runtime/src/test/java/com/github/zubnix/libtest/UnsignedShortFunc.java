package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.Functor;
import com.github.zubnix.jaccall.Unsigned;

/**
 * Created by zubzub on 2/19/16.
 */
@Functor
public interface UnsignedShortFunc {
    @Unsigned
    short $(@Unsigned short value);
}
