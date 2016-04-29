package org.freedesktop.libtest;

import org.freedesktop.jaccall.CType;
import org.freedesktop.jaccall.Field;
import org.freedesktop.jaccall.Struct;

@Struct(union = true,
        value = {
                @Field(type = CType.INT,
                       name = "field0"),
                @Field(type = CType.FLOAT,
                       name = "field1"),
        })
public final class TestUnion extends TestUnion_Jaccall_StructType {
}
