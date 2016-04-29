package org.freedesktop.libtest;


import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Pointer;
import org.freedesktop.jaccall.Size;
import org.freedesktop.jaccall.StructType;
import org.freedesktop.jaccall.Types;

abstract class TestStructFunctionPointer_Jaccall_StructType extends StructType {

    public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_POINTER);
    public static final int  SIZE     = JNI.ffi_type_struct_size(FFI_TYPE);

    private static final int OFFSET_0 = 0;
    private static final int OFFSET_1 = Types.newOffset(Types.alignment((Pointer) null),
                                                        OFFSET_0 + Size.sizeof((Pointer) null));

    TestStructFunctionPointer_Jaccall_StructType() {
        super(SIZE);
    }

    public final Pointer<LongFunc> field0() {
        return readPointer(OFFSET_0,
                           LongFunc.class);
    }

    public final void field0(final Pointer<LongFunc> field0) {
        writePointer(OFFSET_0,
                     field0);
    }

    public final Pointer<Pointer<IntFunc>> field1() {
        return readArray(OFFSET_1,
                         IntFunc.class).castpp();
    }
}
