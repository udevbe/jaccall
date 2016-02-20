package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.CType;
import com.github.zubnix.jaccall.Field;
import com.github.zubnix.jaccall.Struct;

@Struct({
                @Field(name = "field0",
                       type = CType.POINTER,
                       dataType = LongFunc.class),
                @Field(name = "field1",
                       type = CType.POINTER,
                       pointerDepth = 1,
                       dataType = IntFunc.class,
                       cardinality = 3)
        })
public final class TestStructFunctionPointer extends TestStructFunctionPointer_Jaccall_StructType {
}
