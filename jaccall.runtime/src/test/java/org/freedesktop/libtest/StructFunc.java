package org.freedesktop.libtest;

import org.freedesktop.jaccall.ByVal;
import org.freedesktop.jaccall.Functor;
import org.freedesktop.jaccall.Lng;
import org.freedesktop.jaccall.Ptr;
import org.freedesktop.jaccall.Unsigned;

@Functor
public interface StructFunc {
    @ByVal(TestStruct.class)
    long $(@Ptr(TestStruct.class) long tst,
           byte field0,
           @Unsigned short field1,
           @Ptr(int.class) long field2,
           @Ptr(int.class) long field3,
           @Lng long embedded_field0,
           float embedded_field1);
}
