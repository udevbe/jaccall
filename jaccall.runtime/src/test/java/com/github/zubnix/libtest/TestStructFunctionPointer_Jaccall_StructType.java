package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.Size;
import com.github.zubnix.jaccall.StructType;
import com.github.zubnix.jaccall.Types;

abstract class TestStructFunctionPointer_Jaccall_StructType extends StructType {

    public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_POINTER);
    public static final int  SIZE     = JNI.ffi_type_struct_size(FFI_TYPE);

    private static final int OFFSET_0 = 0;
    private static final int OFFSET_1 = Types.newOffset(Types.alignment((Pointer) null),
                                                        OFFSET_0 + Size.sizeof((Pointer) null));

    TestStructFunctionPointer_Jaccall_StructType() {
        super(SIZE);
    }

    public final PointerLongFunc field0() {
        return (PointerLongFunc) readPointer(OFFSET_0,
                                             PointerLongFunc.class);
    }

    public final void field0(final Pointer<LongFunc> field0) {
        writePointer(OFFSET_0,
                     field0);
    }

    public final Pointer<PointerIntFunc> field1() {
        return readArray(OFFSET_1,
                         PointerIntFunc.class);
    }
}
