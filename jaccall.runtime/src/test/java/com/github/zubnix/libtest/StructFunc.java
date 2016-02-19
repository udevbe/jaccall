package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.Functor;
import com.github.zubnix.jaccall.Lng;
import com.github.zubnix.jaccall.Ptr;
import com.github.zubnix.jaccall.Unsigned;

/**
 * Created by zubzub on 2/19/16.
 */
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
