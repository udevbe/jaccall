package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.Functor;
import com.github.zubnix.jaccall.Ptr;

/**
 * Created by zubzub on 2/19/16.
 */
@Functor
public interface PointerFunc {
    @Ptr
    long $(@Ptr long value);
}
