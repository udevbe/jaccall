package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.StructType;

public abstract class TestUnion_Jaccall_StructType extends StructType {

    public static final long FFI_TYPE = JNI.ffi_type_union(JNI.FFI_TYPE_SINT32,
                                                           JNI.FFI_TYPE_FLOAT);
    public static final int  SIZE     = JNI.ffi_type_struct_size(FFI_TYPE);

    TestUnion_Jaccall_StructType() {
        super(SIZE);
    }

    public final int field0() {
        return buffer().get(0);
    }

    public final void field0(int field0) {
        buffer().putInt(0,
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
