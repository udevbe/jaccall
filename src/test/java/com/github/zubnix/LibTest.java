package com.github.zubnix;

import com.github.zubnix.runtime.api.Lib;
import com.github.zubnix.runtime.api.Ptr;
import com.github.zubnix.runtime.api.StructByVal;
import com.github.zubnix.runtime.api.UnionByVal;

@Lib("test")
public class LibTest {
    public native void noArgRVoid();

    public native void intRVoid(int someInt);

    @Ptr
    public native long pointerRPointer(@Ptr long voidPointer);

    @UnionByVal
    public native long structRUnion(@StructByVal long structPointer);
}
