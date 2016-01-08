package com.github.zubnix.libtest.struct;

import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.StructType;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.struct.StructGenerator")
abstract class TestStructUnsignedChar_Jaccall_StructType extends StructType {
    public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_UINT8);

    public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);

    private static final int OFFSET_0 = 0;

    TestStructUnsignedChar_Jaccall_StructType() {
        super(SIZE);
    }

    public final short field0() {
        return readShort(OFFSET_0);
    }

    public final void field0(final short field0) {
        writeShort(OFFSET_0,
                  field0);
    }
}
