package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.StructType;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.StructGenerator")
abstract class TestUnion_Jaccall_StructType extends StructType {

    public static final long FFI_TYPE = JNI.ffi_type_union(JNI.FFI_TYPE_SINT32,
                                                           JNI.FFI_TYPE_FLOAT);
    public static final int  SIZE     = JNI.ffi_type_struct_size(FFI_TYPE);

    TestUnion_Jaccall_StructType() {
        super(SIZE);
    }

    public final int field0() {
        return getInteger(0);
    }

    public final void field0(int field0) {
        setInteger(0,
                   field0);
    }

    public final float field1() {
        return getFloat(0);
    }

    public final void field1(float field1) {
        setFloat(0,
                 field1);
    }
}
