package org.freedesktop.libtest;

import org.freedesktop.jaccall.ByVal;
import org.freedesktop.jaccall.Functor;
import org.freedesktop.jaccall.Ptr;

/**
 * Created by zubzub on 2/19/16.
 */
@Functor
public interface UnionFunc2 {
    @Ptr(TestUnion.class)
    long $(@ByVal(TestUnion.class) long tst,
           int field0);
}
