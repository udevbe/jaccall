package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.Lib;
import com.github.zubnix.jaccall.Ptr;

@Lib("testing")
public class Testing {
    @ByVal(TestStruct.class)
    public static native long doTest(@Ptr(TestStruct.class) long tst,
                                     byte field0,
                                     short field1,
                                     int field2,
                                     @Ptr(int.class) long field3);
}
