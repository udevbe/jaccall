package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.Field;
import com.github.zubnix.jaccall.Struct;

import static com.github.zubnix.jaccall.CType.CHAR;
import static com.github.zubnix.jaccall.CType.INT;
import static com.github.zubnix.jaccall.CType.POINTER;
import static com.github.zubnix.jaccall.CType.SHORT;

@Struct(value = {
        @Field(type = CHAR,
               name = "field0"),
        @Field(type = SHORT,
               name = "field1"),
        @Field(type = INT,
               name = "field2"),
        @Field(type = POINTER,
               dataType = int.class,
               name = "field3")
        //TODO embedded struct
//        ,
//                @Field(type = CType.STRUCT,
//                       dataType = TestStruct.TestStructEmbedded.class,
//                       name = "field4")
})
public final class TestStruct extends Jaccall_TestStruct {


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
