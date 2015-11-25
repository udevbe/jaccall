package com.github.zubnix;

import com.github.zubnix.runtime.api.Field;
import com.github.zubnix.runtime.api.Struct;
import com.github.zubnix.runtime.api.Type;

@Struct(value = {
                @Field(type = Type.CHAR,
                       name = "field0"),
                @Field(type = Type.SHORT,
                       name = "field1"),
                @Field(type = Type.INT,
                       name = "field2"),
                @Field(type = Type.POINTER,
                       name = "field3"),
                @Field(type = Type.STRUCT,
                       embed = StructTest.StructEmbedTest.class,
                       name = "field4")
        })
public class StructTest extends Jaccall_StructTest {

    @Struct(value = {
                    @Field(type = Type.FLOAT,
                           name = "field0"),
                    @Field(type = Type.DOUBLE,
                           name = "field1")
            })
    public static class StructEmbedTest extends Jaccall_StructEmbedTest {

    }
}
