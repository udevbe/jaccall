package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.Functor;
import com.github.zubnix.jaccall.Lib;
import com.github.zubnix.jaccall.Lng;
import com.github.zubnix.jaccall.Ptr;
import com.github.zubnix.jaccall.Unsigned;

@Lib("testing")
public class Testing {

    @Ptr
    public native long charTestFunctionPointer();

    @Ptr
    public native long unsignedCharTestFunctionPointer();

    @Ptr
    public native long shortTestFunctionPointer();

    @Ptr
    public native long unsignedShortTestFunctionPointer();

    @Ptr
    public native long intTestFunctionPointer();

    @Ptr
    public native long unsignedIntTestFunctionPointer();

    @Ptr
    public native long longTestFunctionPointer();

    @Ptr
    public native long unsignedLongTestFunctionPointer();

    @Ptr
    public native long longLongTestFunctionPointer();

    @Ptr
    public native long unsignedLongLongTestFunctionPointer();

    @Ptr
    public native long floatTestFunctionPointer();

    @Ptr
    public native long doubleTestFunctionPointer();

    @Ptr
    public native long pointerTestFunctionPointer();

    @Ptr
    public static native long structTestFunctionPointer();

    @Ptr
    public static native long structTest2FunctionPointer();

    @Ptr
    public static native long unionTestFunctionPointer();

    @Ptr
    public static native long unionTest2FunctionPointer();

    @Ptr
    public native long getFunctionPointerTest();

    public native byte functionPointerTest(@Ptr(TestFunc.class) long function,
                                           @Ptr(TestStruct.class) long arg0,
                                           @Unsigned int arg1,
                                           @ByVal(TestStruct.class) long arg2);

    public native byte charTest(byte value);

    @Functor
    public interface CharTest {
        byte $(byte value);
    }

    @Unsigned
    public native byte unsignedCharTest(@Unsigned byte value);

    @Functor
    public interface UnsignedCharTest {
        @Unsigned
        byte $(@Unsigned byte value);
    }

    public native short shortTest(short value);

    @Functor
    public interface ShortTest {
        short $(short value);
    }

    @Unsigned
    public native short unsignedShortTest(@Unsigned short value);

    @Functor
    public interface UnsignedShortTest {
        @Unsigned
        short $(@Unsigned short value);
    }

    public native int intTest(int value);

    @Functor
    public interface IntTest {
        int $(int value);
    }

    @Unsigned
    public native int unsignedIntTest(@Unsigned int value);

    @Functor
    public interface UnsignedIntTest {

        @Unsigned
        int $(@Unsigned int value);
    }

    public native long longTest(long value);

    @Functor
    public interface LongTest {
        long $(long value);
    }

    @Unsigned
    public native long unsignedLongTest(@Unsigned long value);

    @Functor
    public interface UnsignedLongTest {
        long $(@Unsigned long value);
    }

    @Lng
    public native long longLongTest(@Lng long value);

    @Functor
    public interface LongLongTest {
        @Lng
        long $(@Lng long value);
    }

    @Unsigned
    @Lng
    public native long unsignedLongLongTest(@Unsigned @Lng long value);

    @Functor
    public interface UnsignedLongLongTest {
        @Unsigned
        @Lng
        long $(@Unsigned @Lng long value);
    }

    public native float floatTest(float value);

    @Functor
    public interface FloatTest {
        float $(float value);
    }

    public native double doubleTest(double value);

    @Functor
    interface DoubleTest {
        double $(double value);
    }

    @Ptr
    public native long pointerTest(@Ptr long value);

    @Functor
    interface PointerTest {
        @Ptr
        long $(@Ptr long value);
    }

    @ByVal(TestStruct.class)
    public static native long structTest(@Ptr(TestStruct.class) long tst,
                                         byte field0,
                                         @Unsigned short field1,
                                         @Ptr(int.class) long field2,
                                         @Ptr(int.class) long field3,
                                         @Lng long embedded_field0,
                                         float embedded_field1);

    @Functor
    interface StructTest {
        @ByVal(TestStruct.class)
        long $(@Ptr(TestStruct.class) long tst,
               byte field0,
               @Unsigned short field1,
               @Ptr(int.class) long field2,
               @Ptr(int.class) long field3,
               @Lng long embedded_field0,
               float embedded_field1);
    }

    @Ptr(TestStruct.class)
    public static native long structTest2(@ByVal(TestStruct.class) long tst,
                                          byte field0,
                                          @Unsigned short field1,
                                          @Ptr(int.class) long field2,
                                          @Ptr(int.class) long field3,
                                          @Lng long embedded_field0,
                                          float embedded_field1);

    @Functor
    interface StructTest2 {
        @Ptr(TestStruct.class)
        long $(@ByVal(TestStruct.class) long tst,
               byte field0,
               @Unsigned short field1,
               @Ptr(int.class) long field2,
               @Ptr(int.class) long field3,
               @Lng long embedded_field0,
               float embedded_field1);
    }

    @ByVal(TestUnion.class)
    public static native long unionTest(@Ptr(TestUnion.class) long tst,
                                        int field0,
                                        float field1);

    @Functor
    interface UnionTest {
        @ByVal(TestUnion.class)
        long $(@Ptr(TestUnion.class) long tst,
               int field0,
               float field1);
    }

    @Ptr(TestUnion.class)
    public static native long unionTest2(@ByVal(TestUnion.class) long tst,
                                         int field0);

    @Functor
    interface UnionTest2 {
        @Ptr(TestUnion.class)
        long $(@ByVal(TestUnion.class) long tst,
               int field0);
    }

    public native void noArgsTest();

    @Ptr
    public native long noArgsFuncPtrTest();

}
