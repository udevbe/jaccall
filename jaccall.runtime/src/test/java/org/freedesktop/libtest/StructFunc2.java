package org.freedesktop.libtest;

import org.freedesktop.jaccall.ByVal;
import org.freedesktop.jaccall.Functor;
import org.freedesktop.jaccall.Lng;
import org.freedesktop.jaccall.Ptr;
import org.freedesktop.jaccall.Unsigned;

/**
 * Created by zubzub on 2/19/16.
 */
@Functor
public interface StructFunc2 {
    @Ptr(TestStruct.class)
    long $(@ByVal(TestStruct.class) long tst,
           byte field0,
           @Unsigned short field1,
           @Ptr(int.class) long field2,
           @Ptr(int.class) long field3,
           @Lng long embedded_field0,
           float embedded_field1);
}
