package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.CType;
import com.github.zubnix.jaccall.Field;
import com.github.zubnix.jaccall.Struct;

@Struct(union = true,
        value = {
                @Field(type = CType.SHORT,
                       name = "field0"),
                @Field(type = CType.FLOAT,
                       name = "field1"),
        })
public final class TestUnion extends TestUnion_Jaccall_StructType {
}
