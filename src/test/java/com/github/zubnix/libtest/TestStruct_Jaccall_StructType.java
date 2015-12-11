package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.Size;
import com.github.zubnix.jaccall.StructType;

import javax.annotation.Generated;

//TODO auto generate this code from a compile time annotation processor
@Generated("com.github.zubnix.jaccall.compiletime.StructGenerator")
abstract class TestStruct_Jaccall_StructType extends StructType {

    private static final int OFFSET_0 = 0;
    private static final int OFFSET_1 = newOffset(Size.sizeof((Short) null),
                                                  OFFSET_0 + Size.sizeof((Byte) null));
    private static final int OFFSET_2 = newOffset(Size.sizeof((Integer) null),
                                                  OFFSET_1 + Size.sizeof((Short) null));
    private static final int OFFSET_3 = newOffset(Size.sizeof((Pointer<?>) null),
                                                  OFFSET_2 + Size.sizeof((Integer) null));

    public static final int SIZE = OFFSET_3 + Size.sizeof((Pointer<?>) null);

    TestStruct_Jaccall_StructType() {
        super(SIZE);
    }

    public byte field0() {
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
        final long pointerSize = Size.sizeof((Pointer) null);
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
        final long pointerSize = Size.sizeof((Pointer) null);

        if (pointerSize == 8) {
            buffer().putLong(OFFSET_3,
                             field3.cast(Long.class));
        }
        else {
            buffer().putInt(OFFSET_3,
                            field3.cast(Integer.class));
        }
    }
}