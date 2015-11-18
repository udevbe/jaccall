package com.github.zubnix.jaccall.compiletime.linker.testcasesource;

import com.github.zubnix.jaccall.Lib;
import com.github.zubnix.jaccall.Ptr;
import com.github.zubnix.jaccall.Unsigned;

public class LibNotOnClass {
    @Lib("testing")
    public interface Testing {
        long doTest(long tst,
                    byte field0,
                    @Unsigned short field1,
                    @Ptr(int.class) long field2,
                    @Ptr(int.class) long field3);
    }
}