package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.runtime.DataType;
import com.github.zubnix.jaccall.runtime.api.CType;
import com.github.zubnix.jaccall.runtime.api.Field;
import com.github.zubnix.jaccall.runtime.api.Pointer;

import javax.annotation.Generated;

import static com.github.zubnix.jaccall.runtime.Size.sizeOf;
import static com.github.zubnix.jaccall.runtime.api.Pointer.malloc;

@Generated("com.github.zubnix.jaccall.compiletime.Generator")
class Jaccall_TestStruct extends DataType<TestStruct> {

    private static long SIZE = -1;

    private final Pointer<TestStruct> pointer;

    Jaccall_TestStruct(final Class<TestStruct> clazz) {
        if (SIZE < 0) {
            SIZE = sizeOf(this);
        }
        this.pointer = malloc(SIZE).cast(clazz);
    }

    @Override
    protected Pointer<TestStruct> getAddress() {
        return pointer;
    }

    public byte field() {

    }

    public void field0(byte field0) {

    }

    public short field1() {

    }

    public void field1(short field1) {

    }

    public int field2() {

    }


    public void field2(int field2) {

    }

    public Pointer<Integer> field3() {

    }

    public void field3(Pointer<Integer> field3) {

    }


    @Field(type = CType.POINTER,
           name = "field3") ,
    @Field(type = CType.STRUCT,
           dataType = TestStruct.TestStructEmbedded.class,
           name = "field4") }
