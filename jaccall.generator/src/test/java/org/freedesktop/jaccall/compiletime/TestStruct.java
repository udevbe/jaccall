package org.freedesktop.jaccall.compiletime;

import org.freedesktop.jaccall.CType;
import org.freedesktop.jaccall.Field;
import org.freedesktop.jaccall.Struct;

import static org.freedesktop.jaccall.CType.CHAR;
import static org.freedesktop.jaccall.CType.INT;
import static org.freedesktop.jaccall.CType.POINTER;
import static org.freedesktop.jaccall.CType.UNSIGNED_SHORT;

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
public final class TestStruct extends TestStruct_Jaccall_StructType
{
}
