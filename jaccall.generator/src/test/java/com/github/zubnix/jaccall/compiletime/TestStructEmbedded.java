package com.github.zubnix.jaccall.compiletime;

import com.github.zubnix.jaccall.CType;
import com.github.zubnix.jaccall.Field;
import com.github.zubnix.jaccall.Struct;

@Struct(value = {
        @Field(type = CType.LONG_LONG,
               name = "field0"),
        @Field(type = CType.FLOAT,
               name = "field1")
})
public final class TestStructEmbedded extends TestStructEmbedded_Jaccall_StructType {
}
