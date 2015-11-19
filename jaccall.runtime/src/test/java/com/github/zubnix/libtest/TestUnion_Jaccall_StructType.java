package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.Size;
import com.github.zubnix.jaccall.StructType;

public class TestUnion_Jaccall_StructType extends StructType {

    public static final int SIZE = MAX(Size.sizeof((byte) 0),
                                       Size.sizeof((float) 0));

    TestUnion_Jaccall_StructType() {
        super(SIZE);
    }

    public final byte field0() {
        return buffer().get(0);
    }

    public final void field0(byte field0) {
        buffer().put(0,
                     field0);
    }

    public final float field1() {
        return buffer().getFloat(0);
    }

    public final void field1(float field0) {
        buffer().putFloat(0,
                          field0);
    }
}
