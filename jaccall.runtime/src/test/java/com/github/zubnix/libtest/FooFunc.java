package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.Functor;
import com.github.zubnix.jaccall.Ptr;
import com.github.zubnix.jaccall.Unsigned;

@Functor
public interface FooFunc {

    byte $(@Ptr long arg0,
           @Unsigned int arg1,
           @ByVal(TestStruct.class) long arg2);
}
