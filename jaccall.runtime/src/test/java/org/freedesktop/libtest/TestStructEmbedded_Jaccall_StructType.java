package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Size;
import org.freedesktop.jaccall.StructType;
import org.freedesktop.jaccall.Types;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.StructGenerator")
abstract class TestStructEmbedded_Jaccall_StructType extends StructType {

    public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_SINT64,
                                                            JNI.FFI_TYPE_FLOAT);

    public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);

    private static final int OFFSET_0 = 0;

    private static final int OFFSET_1 = Types.newOffset(Types.alignment((Float) null),
                                                        OFFSET_0 + (Size.sizeof((Long) null) * 1));

    TestStructEmbedded_Jaccall_StructType() {
        super(SIZE);
    }

    public final long field0() {
        return getLong(OFFSET_0);
    }

    public final void field0(final long field0) {
        setLong(OFFSET_0,
                  field0);
    }

    public final float field1() {
        return getFloat(OFFSET_1);
    }

    public final void field1(final float field1) {
        setFloat(OFFSET_1,
                   field1);
    }
}
