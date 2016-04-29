package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.Size;
import org.freedesktop.jaccall.StructType;
import org.freedesktop.jaccall.Types;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.StructGenerator")
abstract class TestStruct_Jaccall_StructType extends StructType {
    public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_SINT8,
                                                            JNI.FFI_TYPE_UINT16,
                                                            JNI.FFI_TYPE_UINT32,
                                                            JNI.FFI_TYPE_UINT32,
                                                            JNI.FFI_TYPE_UINT32,
                                                            JNI.FFI_TYPE_POINTER,
                                                            TestStructEmbedded.FFI_TYPE);

    public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);

    private static final int OFFSET_0 = 0;

    private static final int OFFSET_1 = Types.newOffset(Types.alignment((Short) null),
                                                        OFFSET_0 + (Size.sizeof((Byte) null) * 1));

    private static final int OFFSET_2 = Types.newOffset(Types.alignment((Integer) null),
                                                        OFFSET_1 + (Size.sizeof((Short) null) * 1));

    private static final int OFFSET_3 = Types.newOffset(Types.alignment((Pointer) null),
                                                        OFFSET_2 + (Size.sizeof((Integer) null) * 3));

    private static final int OFFSET_4 = Types.newOffset(Types.alignment((Long) null),
                                                        OFFSET_3 + (Size.sizeof((Pointer) null) * 1));

    TestStruct_Jaccall_StructType() {
        super(SIZE);
    }

    public final byte field0() {
        return readByte(OFFSET_0);
    }

    public final void field0(final byte field0) {
        writeByte(OFFSET_0,
                  field0);
    }

    public final short field1() {
        return readShort(OFFSET_1);
    }

    public final void field1(final short field1) {
        writeShort(OFFSET_1,
                   field1);
    }

    public final Pointer<Integer> field2() {
        return readArray(OFFSET_2,
                         Integer.class);
    }

    public final Pointer<Integer> field3() {
        return readPointer(OFFSET_3,
                           Integer.class);
    }

    public final void field3(final Pointer<Integer> field3) {
        writePointer(OFFSET_3,
                     field3);
    }

    public final TestStructEmbedded field4() {
        return readStructType(OFFSET_4,
                              TestStructEmbedded.class);
    }

    public final void field4(final TestStructEmbedded field4) {
        writeStructType(OFFSET_4,
                        field4);
    }
}
