package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.CType;
import com.github.zubnix.jaccall.Field;
import com.github.zubnix.jaccall.Struct;

@Struct({
                @Field(name = "charField",
                       type = CType.CHAR),
                @Field(name = "shortField",
                       type = CType.SHORT),
                @Field(name = "intField",
                       type = CType.INT),
                @Field(name = "longField",
                       type = CType.LONG),
                @Field(name = "longLongField",
                       type = CType.LONG_LONG),
                @Field(name = "floatField",
                       type = CType.FLOAT),
                @Field(name = "doubleField",
                       type = CType.DOUBLE),
                @Field(name = "pointerField",
                       type = CType.POINTER,
                       dataType = Void.class),
                @Field(name = "pointerArrayField",
                       type = CType.POINTER,
                       dataType = Void.class,
                       cardinality = 3),
                @Field(name = "structField",
                       type = CType.STRUCT,
                       dataType = TestStructEmbedded.class),
                @Field(name = "structArrayField",
                       type = CType.STRUCT,
                       dataType = TestStructEmbedded.class,
                       cardinality = 3),
        })
public final class FieldsTestStruct extends FieldsTestStruct_Jaccall_StructType {
}
