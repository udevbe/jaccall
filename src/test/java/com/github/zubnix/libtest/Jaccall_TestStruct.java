package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.runtime.JNI;
import com.github.zubnix.jaccall.runtime.Pointer;
import com.github.zubnix.jaccall.runtime.Size;
import com.github.zubnix.jaccall.runtime.StructSignature;
import com.github.zubnix.jaccall.runtime.StructType;

import javax.annotation.Generated;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

//TODO auto generate this code from a compile time annotation processor
@Generated("com.github.zubnix.jaccall.compiletime.StructGenerator")
@StructSignature("wxyz")
public class Jaccall_TestStruct extends StructType {

    private static final long      DC_STRUCT     = JNI.dcDefineStruct(Jaccall_TestStruct.class.getAnnotation(StructSignature.class)
                                                                                              .value());
    private static final IntBuffer FIELD_OFFSETS = JNI.dcStructFieldOffsets(DC_STRUCT,
                                                                            ByteBuffer.allocateDirect(8 * 4))
                                                      .asIntBuffer();

    private static final int OFFSET_0 = FIELD_OFFSETS.get();
    private static final int OFFSET_1 = FIELD_OFFSETS.get();
    private static final int OFFSET_2 = FIELD_OFFSETS.get();
    private static final int OFFSET_3 = FIELD_OFFSETS.get();

    public byte field() {
        return buffer().get(OFFSET_0);
    }

    public void field0(byte field0) {
        buffer().put(OFFSET_0,
                     field0);
    }

    public short field1() {
        return buffer().getShort(OFFSET_1);
    }

    public void field1(short field1) {
        buffer().putShort(OFFSET_1,
                          field1);
    }

    public int field2() {
        return buffer().getInt(OFFSET_2);
    }


    public void field2(int field2) {
        buffer().putInt(OFFSET_2,
                        field2);
    }

    public Pointer<Integer> field3() {
        final long pointerSize = Size.sizeOf((Pointer) null);
        final long address;

        if (pointerSize == 8) {
            address = buffer().getLong(OFFSET_3);
        }
        else {
            address = buffer().getInt(OFFSET_3);
        }

        return Pointer.wrap(Integer.class,
                            address);
    }

    public void field3(Pointer<Integer> field3) {
        final long pointerSize = Size.sizeOf((Pointer) null);

        if (pointerSize == 8) {
            buffer().putLong(OFFSET_3,
                             field3.tCast(Long.class));
        }
        else {
            buffer().putInt(OFFSET_3,
                            field3.tCast(Integer.class));
        }
    }
}
