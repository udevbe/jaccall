package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.LinkSymbols;
import com.github.zubnix.jaccall.Ptr;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.LinkerGenerator")
public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {
    public Testing_Jaccall_LinkSymbols() {
        super(new String[]{"doTest", "doStaticTest"},
              new String[]{"pcsip)tcsip]", "pcsip)tcsip]"});
    }

    @ByVal(TestStruct.class)
    public native long doTest(@Ptr(TestStruct.class) long tst,
                              byte field0,
                              short field1,
                              int field2,
                              @Ptr(int.class) long field3);
}
