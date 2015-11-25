package com.github.zubnix.jaccall.compiletime;

import com.github.zubnix.jaccall.CType;
import com.github.zubnix.jaccall.Field;
import com.github.zubnix.jaccall.Struct;

import static com.github.zubnix.jaccall.CType.CHAR;
import static com.github.zubnix.jaccall.CType.INT;
import static com.github.zubnix.jaccall.CType.POINTER;
import static com.github.zubnix.jaccall.CType.UNSIGNED_SHORT;

@Struct(value = {
        @Field(type = CHAR,
               name = "field0"),
        @Field(type = UNSIGNED_SHORT,
               name = "field1"),
        @Field(type = INT,
               cardinality = 3,
               name = "field2"),
        @Field(type = POINTER,
               dataType = int.class,
               name = "field3"),
        @Field(type = CType.STRUCT,
               dataType = TestStructEmbedded.class,
               name = "field4")
})
public final class TestStruct extends TestStruct_Jaccall_StructType {
}
