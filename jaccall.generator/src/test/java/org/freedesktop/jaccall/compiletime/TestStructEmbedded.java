package org.freedesktop.jaccall.compiletime;

import org.freedesktop.jaccall.CType;
import org.freedesktop.jaccall.Field;
import org.freedesktop.jaccall.Struct;

@Struct(value = {
        @Field(type = CType.LONG_LONG,
               name = "field0"),
        @Field(type = CType.FLOAT,
               name = "field1")
})
public final class TestStructEmbedded extends Struct_TestStructEmbedded
{
}
