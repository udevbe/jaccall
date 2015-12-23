package com.github.zubnix.jaccall.compiletime.linker;

import com.github.zubnix.jaccall.Field;
import com.github.zubnix.jaccall.Struct;

import static com.github.zubnix.jaccall.CType.FLOAT;
import static com.github.zubnix.jaccall.CType.LONG_LONG;


@Struct(value = {
        @Field(type = LONG_LONG,
               name = "embedded_field0"),
        @Field(type = FLOAT,
               name = "embedded_field1")
})
public class TestStructEmbedded extends TestStructEmbedded_Jaccall_StructType {

}
