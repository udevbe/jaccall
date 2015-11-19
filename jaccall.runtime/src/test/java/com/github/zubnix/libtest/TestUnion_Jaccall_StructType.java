package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.StructType;

public class TestUnion_Jaccall_StructType extends StructType {

    public static final long FFI_TYPE = JNI.FFI_UNION_TYPE(JNI.FFI_TYPE('s'),
                                                           JNI.FFI_TYPE('f'));
    public static final int  SIZE     = JNI.FFI_STRUCT_TYPE_SIZE(FFI_TYPE);

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
