package org.freedesktop.libtest;

import org.freedesktop.jaccall.CLong;
import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.Size;
import org.freedesktop.jaccall.StructType;
import org.freedesktop.jaccall.Types;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.struct.StructGenerator")
abstract class FieldsTestStruct_Jaccall_StructType extends StructType {
    public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_SINT8,
                                                            JNI.FFI_TYPE_SINT16,
                                                            JNI.FFI_TYPE_SINT32,
                                                            JNI.FFI_TYPE_SLONG,
                                                            JNI.FFI_TYPE_SINT64,
                                                            JNI.FFI_TYPE_FLOAT,
                                                            JNI.FFI_TYPE_DOUBLE,
                                                            JNI.FFI_TYPE_POINTER,
                                                            JNI.FFI_TYPE_POINTER,
                                                            JNI.FFI_TYPE_POINTER,
                                                            JNI.FFI_TYPE_POINTER,
                                                            TestStructEmbedded.FFI_TYPE,
                                                            TestStructEmbedded.FFI_TYPE,
                                                            TestStructEmbedded.FFI_TYPE,
                                                            TestStructEmbedded.FFI_TYPE);

    public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);

    private static final int OFFSET_0 = 0;

    private static final int OFFSET_1 = Types.newOffset(Types.alignment((Short) null),
                                                        OFFSET_0 + (Size.sizeof((Byte) null) * 1));

    private static final int OFFSET_2 = Types.newOffset(Types.alignment((Integer) null),
                                                        OFFSET_1 + (Size.sizeof((Short) null) * 1));

    private static final int OFFSET_3 = Types.newOffset(Types.alignment((CLong) null),
                                                        OFFSET_2 + (Size.sizeof((Integer) null) * 1));

    private static final int OFFSET_4 = Types.newOffset(Types.alignment((Long) null),
                                                        OFFSET_3 + (Size.sizeof((CLong) null) * 1));

    private static final int OFFSET_5 = Types.newOffset(Types.alignment((Float) null),
                                                        OFFSET_4 + (Size.sizeof((Long) null) * 1));

    private static final int OFFSET_6 = Types.newOffset(Types.alignment((Double) null),
                                                        OFFSET_5 + (Size.sizeof((Float) null) * 1));

    private static final int OFFSET_7 = Types.newOffset(Types.alignment((Pointer) null),
                                                        OFFSET_6 + (Size.sizeof((Double) null) * 1));

    private static final int OFFSET_8 = Types.newOffset(Types.alignment((Pointer) null),
                                                        OFFSET_7 + (Size.sizeof((Pointer) null) * 1));

    private static final int OFFSET_9 = Types.newOffset(Types.alignment((Long) null),
                                                        OFFSET_8 + (Size.sizeof((Pointer) null) * 3));

    private static final int OFFSET_10 = Types.newOffset(Types.alignment((Long) null),
                                                         OFFSET_9 + (TestStructEmbedded.SIZE * 1));

    FieldsTestStruct_Jaccall_StructType() {
        super(SIZE);
    }

    public final byte charField() {
        return getByte(OFFSET_0);
    }

    public final void charField(final byte charField) {
        setByte(OFFSET_0,
                charField);
    }

    public final short shortField() {
        return getShort(OFFSET_1);
    }

    public final void shortField(final short shortField) {
        setShort(OFFSET_1,
                 shortField);
    }

    public final int intField() {
        return getInteger(OFFSET_2);
    }

    public final void intField(final int intField) {
        setInteger(OFFSET_2,
                   intField);
    }

    public final CLong longField() {
        return getCLong(OFFSET_3);
    }

    public final void longField(final CLong longField) {
        setCLong(OFFSET_3,
                 longField);
    }

    public final long longLongField() {
        return getLong(OFFSET_4);
    }

    public final void longLongField(final long longLongField) {
        setLong(OFFSET_4,
                longLongField);
    }

    public final float floatField() {
        return getFloat(OFFSET_5);
    }

    public final void floatField(final float floatField) {
        setFloat(OFFSET_5,
                 floatField);
    }

    public final double doubleField() {
        return getDouble(OFFSET_6);
    }

    public final void doubleField(final double doubleField) {
        setDouble(OFFSET_6,
                  doubleField);
    }

    public final Pointer<Void> pointerField() {
        return getPointer(OFFSET_7,
                          Void.class);
    }

    public final void pointerField(final Pointer<Void> pointerField) {
        setPointer(OFFSET_7,
                   pointerField);
    }

    public final Pointer<Pointer<Void>> pointerArrayField() {
        return getArray(OFFSET_8,
                        Void.class).castpp();
    }

    public final TestStructEmbedded structField() {
        return getStructType(OFFSET_9,
                             TestStructEmbedded.class);
    }

    public final void structField(final TestStructEmbedded structField) {
        setStructType(OFFSET_9,
                      structField);
    }

    public final Pointer<TestStructEmbedded> structArrayField() {
        return getArray(OFFSET_10,
                        TestStructEmbedded.class);
    }
}
