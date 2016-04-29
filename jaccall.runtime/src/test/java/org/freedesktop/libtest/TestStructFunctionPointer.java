package org.freedesktop.libtest;

import org.freedesktop.jaccall.CType;
import org.freedesktop.jaccall.Field;
import org.freedesktop.jaccall.Struct;

@Struct({
                @Field(name = "field0",
                       type = CType.POINTER,
                       dataType = LongFunc.class),
                @Field(name = "field1",
                       type = CType.POINTER,
                       pointerDepth = 1,
                       dataType = IntFunc.class,
                       cardinality = 3)
        })
public final class TestStructFunctionPointer extends TestStructFunctionPointer_Jaccall_StructType {
}
