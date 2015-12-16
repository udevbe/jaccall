package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.Lib;
import com.github.zubnix.jaccall.Ptr;

@Lib("testing")
public class Testing {
    @ByVal(TestStruct.class)
    public static native long doStaticTest(@Ptr(TestStruct.class) long tst,
                                           byte field0,
                                           short field1,
                                           @Ptr(int.class) long field2,
                                           @Ptr(int.class) long field3);
}
