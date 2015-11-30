package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.runtime.Pointer;
import com.github.zubnix.jaccall.runtime.Struct;
import com.github.zubnix.jaccall.runtime.StructType;

import javax.annotation.Generated;

import static com.github.zubnix.jaccall.runtime.Size.sizeOf;

@Generated("com.github.zubnix.jaccall.compiletime.StructGenerator")
public class Jaccall_TestStruct extends StructType {

    private static final long SIZE = sizeOf(TestStruct.class.getAnnotation(Struct.class));

    //TODO how to dynamically determine field offset?

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

    @Override
    protected long size() {
        return SIZE;
    }
}
