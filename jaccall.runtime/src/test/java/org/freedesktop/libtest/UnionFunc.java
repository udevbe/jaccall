package org.freedesktop.libtest;

import org.freedesktop.jaccall.ByVal;
import org.freedesktop.jaccall.Functor;
import org.freedesktop.jaccall.Ptr;

/**
 * Created by zubzub on 2/19/16.
 */
@Functor
public interface UnionFunc {
    @ByVal(TestUnion.class)
    long invoke(@Ptr(TestUnion.class) long tst,
           int field0,
           float field1);
}
