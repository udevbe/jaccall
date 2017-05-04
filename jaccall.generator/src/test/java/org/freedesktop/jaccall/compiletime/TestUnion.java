package org.freedesktop.jaccall.compiletime;

import org.freedesktop.jaccall.Field;
import org.freedesktop.jaccall.Struct;

import static org.freedesktop.jaccall.CType.CHAR;
import static org.freedesktop.jaccall.CType.FLOAT;
import static org.freedesktop.jaccall.CType.UNSIGNED_INT;

@Struct(union = true,
        value = {
                @Field(type = FLOAT,
                       name = "field0"),
                @Field(type = CHAR,
                       name = "field1"),
                @Field(type = UNSIGNED_INT,
                       name = "field2"),
        })
public final class TestUnion extends Struct_TestUnion
{

}
