package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.Lib;
import com.github.zubnix.jaccall.Ptr;

@Lib("test")
public class Test {
    public native void noArgRVoid();

    public native void intRVoid(int someInt);

    @Ptr
    public native long pointerRPointer(@Ptr long voidPointer);
}
