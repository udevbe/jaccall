package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.StructType;
import com.github.zubnix.jaccall.Types;

import javax.annotation.Generated;

import static com.github.zubnix.jaccall.Size.sizeof;
import static com.github.zubnix.jaccall.Types.alignment;

@Generated("com.github.zubnix.jaccall.compiletime.StructGenerator")
abstract class TestStructEmbedded_Jaccall_StructType extends StructType {

    public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_SINT64,
                                                            JNI.FFI_TYPE_FLOAT);
    public static final int  SIZE     = JNI.ffi_type_struct_size(FFI_TYPE);

    private static final int OFFSET_0 = 0;
    private static final int OFFSET_1 = Types.newOffset(alignment((Float) null),
                                                        OFFSET_0 + sizeof((Long) null));

    TestStructEmbedded_Jaccall_StructType() {
        super(SIZE);
    }

    public final long field0() {
        return readLong(OFFSET_0);
    }

    public final void field0(long field0) {
        writeLong(OFFSET_0,
                  field0);
    }

    public final float field1() {
        return readFloat(OFFSET_1);
    }

    public final void field1(float field1) {
        writeFloat(OFFSET_1,
                   field1);
    }
}
