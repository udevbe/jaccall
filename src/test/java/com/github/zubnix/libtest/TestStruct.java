package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.runtime.CType;
import com.github.zubnix.jaccall.runtime.Field;
import com.github.zubnix.jaccall.runtime.Struct;

@Struct(value = {
        @Field(type = CType.CHAR,
               name = "field0"),
        @Field(type = CType.SHORT,
               name = "field1"),
        @Field(type = CType.INT,
               name = "field2"),
        @Field(type = CType.POINTER,
               dataType = int.class,
               name = "field3")
//        ,
//                @Field(type = CType.STRUCT,
//                       dataType = TestStruct.TestStructEmbedded.class,
//                       name = "field4")
})
public class TestStruct extends Jaccall_TestStruct {


//    @Struct(value = {
//                    @Field(type = CType.FLOAT,
//                           name = "field0"),
//                    @Field(type = CType.DOUBLE,
//                           name = "field1")
//            })
//    public static class TestStructEmbedded extends Jaccall_TestStructEmbedded {
//        public TestStructEmbedded() {
//            super(TestStructEmbedded.class);
//        }
//    }

//    public TestStruct() {
//        super(TestStruct.class);
//    }
}
