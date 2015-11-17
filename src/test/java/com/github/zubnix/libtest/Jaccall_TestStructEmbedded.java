package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.runtime.StructType;
import com.github.zubnix.jaccall.runtime.api.CType;
import com.github.zubnix.jaccall.runtime.api.Field;
import com.github.zubnix.jaccall.runtime.api.Pointer;

import javax.annotation.Generated;

import static com.github.zubnix.jaccall.runtime.Size.sizeOf;
import static com.github.zubnix.jaccall.runtime.api.Pointer.malloc;

@Generated("com.github.zubnix.jaccall.compiletime.Generator")
class Jaccall_TestStructEmbedded extends StructType<TestStruct.TestStructEmbedded> {

    private final Pointer<TestStruct.TestStructEmbedded> pointer;

    Jaccall_TestStructEmbedded(Class<TestStruct.TestStructEmbedded> clazz) {
        this.pointer = malloc(sizeOf(this)).cast(clazz);
    }

    @Override
    protected Pointer<TestStruct.TestStructEmbedded> getAddress() {
        return this.pointer;
    }




    @com.github.zubnix.jaccall.runtime.api.Struct(value = {
            @Field(type = CType.FLOAT,
                   name = "field0"),
            @Field(type = CType.DOUBLE,
                   name = "field1")
    }) }
