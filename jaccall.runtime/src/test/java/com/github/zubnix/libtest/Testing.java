package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.Lib;
import com.github.zubnix.jaccall.Ptr;
import com.github.zubnix.jaccall.Unsigned;

@Lib("testing")
public class Testing {


    @ByVal(TestStruct.class)
    public static native long doStaticTest(@Ptr(TestStruct.class) long tst,
                                           byte field0,
                                           @Unsigned short field1,
                                           @Ptr(int.class) long field2,
                                           @Ptr(int.class) long field3);

    @Ptr(TestStruct.class)
    public static native long doStaticTest2(@ByVal(TestStruct.class) long tst,
                                            byte field0,
                                            @Unsigned short field1,
                                            @Ptr(int.class) long field2,
                                            @Ptr(int.class) long field3);

    @ByVal(TestUnion.class)
    public static native long doStaticUnionTest(@Ptr(TestUnion.class) long tst,
                                                int field0,
                                                float field1);

    @Ptr(TestUnion.class)
    public static native long doStaticUnionTest2(@ByVal(TestUnion.class) long tst,
                                                 int field0);
}
