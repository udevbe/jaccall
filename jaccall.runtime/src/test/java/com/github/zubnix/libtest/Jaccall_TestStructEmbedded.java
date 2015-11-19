package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Size;
import com.github.zubnix.jaccall.StructType;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.StructGenerator")
abstract class Jaccall_TestStructEmbedded extends StructType {

    public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.ffi_type_float(),
                                                            JNI.ffi_type_double());
    public static final int  SIZE     = JNI.ffi_type_struct_size(FFI_TYPE);

    private static final int OFFSET_0 = 0;
    private static final int OFFSET_1 = newOffset(Size.sizeof((Double) null),
                                                  OFFSET_0 + Size.sizeof((Float) null));

    Jaccall_TestStructEmbedded() {
        super(SIZE);
    }

    public final float field0() {
        return buffer().getFloat(OFFSET_0);
    }

    public final void field0(float field0) {
        buffer().putFloat(OFFSET_0,
                          field0);
    }

    public final double field1() {
        return buffer().getDouble(OFFSET_1);
    }

    public final void field1(double field1) {
        buffer().putDouble(OFFSET_1,
                           field1);
    }
}
