package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.Functor;
import com.github.zubnix.jaccall.Lng;

/**
 * Created by zubzub on 2/19/16.
 */
@Functor
public interface LongLongFunc {
    @Lng
    long $(@Lng long value);
}
