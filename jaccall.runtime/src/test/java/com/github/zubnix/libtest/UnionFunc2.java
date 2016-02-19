package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.Functor;
import com.github.zubnix.jaccall.Ptr;

/**
 * Created by zubzub on 2/19/16.
 */
@Functor
public interface UnionFunc2 {
    @Ptr(TestUnion.class)
    long $(@ByVal(TestUnion.class) long tst,
           int field0);
}
