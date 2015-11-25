package com.github.zubnix.jaccall.compiletime;

import com.github.zubnix.jaccall.Field;
import com.github.zubnix.jaccall.Struct;

import static com.github.zubnix.jaccall.CType.CHAR;
import static com.github.zubnix.jaccall.CType.FLOAT;
import static com.github.zubnix.jaccall.CType.UNSIGNED_INT;

@Struct(union = true,
        value = {
                @Field(type = FLOAT,
                       name = "field0"),
                @Field(type = CHAR,
                       name = "field1"),
                @Field(type = UNSIGNED_INT,
                       name = "field2"),
        })
public final class TestUnion extends TestUnion_Jaccall_StructType {

}
