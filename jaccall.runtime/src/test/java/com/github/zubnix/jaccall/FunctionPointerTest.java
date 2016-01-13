package com.github.zubnix.jaccall;


import com.github.zubnix.libtest.PointerTestFunc;
import com.github.zubnix.libtest.TestFunc;
import com.github.zubnix.libtest.TestStruct;
import com.github.zubnix.libtest.TestUnion;
import com.github.zubnix.libtest.Testing;
import com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.truth.Truth.assertThat;

public class FunctionPointerTest {

    private static final String LIB_PREFIX  = "lib";
    private static final String LIB_NAME    = "testing";
    private static final String LIB_POSTFIX = ".so";

    private static String libFilePath() {
        final InputStream libStream = JNI.class.getClassLoader()
                                               .getResourceAsStream(LIB_PREFIX + LIB_NAME + LIB_POSTFIX);
        try {
            final File tempFile = File.createTempFile(LIB_NAME,
                                                      null);
            tempFile.deleteOnExit();
            unpack(libStream,
                   tempFile);
            return tempFile.getAbsolutePath();
        }
        catch (final IOException e) {
            throw new Error(e);
        }
    }

    private static void unpack(final InputStream libStream,
                               final File tempFile) throws IOException {
        final FileOutputStream fos    = new FileOutputStream(tempFile);
        final byte[]           buffer = new byte[4096];
        int                    read   = -1;
        while ((read = libStream.read(buffer)) != -1) {
            fos.write(buffer,
                      0,
                      read);
        }
        fos.close();
        libStream.close();
    }

    @Test
    public void testCallFromC() {

        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long            funcPtrAddr     = new Testing().getFunctionPointerTest();
        final PointerTestFunc pointerTestFunc = PointerTestFunc.wrapFunc(funcPtrAddr);

        try (final Pointer<TestStruct> arg0 = Pointer.malloc(Size.sizeof(TestStruct.SIZE))
                                                     .castp(TestStruct.class);
             final Pointer<TestStruct> arg2 = Pointer.malloc(Size.sizeof(TestStruct.SIZE))
                                                     .castp(TestStruct.class);
             final Pointer<Integer> field3 = Pointer.malloc(Size.sizeof((Integer) null))
                                                    .castp(Integer.class)) {

            arg2.dref()
                .field0((byte) 123);
            arg2.dref()
                .field1((short) 345);
            arg2.dref()
                .field3(field3);

            //when
            final byte result = pointerTestFunc.$(arg0.address,
                                                  567,
                                                  arg2.address);

            //then
            assertThat(result).isEqualTo((byte) 123);
            assertThat(arg0.dref()
                           .field2()
                           .dref(0)).isEqualTo(567);
            assertThat(arg0.dref()
                           .field2()
                           .dref(1)).isEqualTo(345);
            assertThat(arg0.dref()
                           .field3().address).isEqualTo(field3.address);
        }
    }

    @Test
    public void testCallFromJava() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final PointerTestFunc pointerTestFunc = PointerTestFunc.nref(new TestFunc() {
            @Override
            public byte $(@Ptr final long arg0,
                          @Unsigned final int arg1,
                          @ByVal(TestStruct.class) final long arg2) {
                return function(arg0,
                                arg1,
                                arg2);
            }
        });

        try (final Pointer<TestStruct> arg0 = Pointer.malloc(Size.sizeof(TestStruct.SIZE))
                                                     .castp(TestStruct.class);
             final Pointer<TestStruct> arg2 = Pointer.malloc(Size.sizeof(TestStruct.SIZE))
                                                     .castp(TestStruct.class);
             final Pointer<Integer> field3 = Pointer.malloc(Size.sizeof((Integer) null))
                                                    .castp(Integer.class)) {

            arg2.dref()
                .field0((byte) 123);
            arg2.dref()
                .field1((short) 345);
            arg2.dref()
                .field3(field3);

            //when
            final byte result = new Testing().functionPointerTest(pointerTestFunc.address,
                                                                  arg0.address,
                                                                  567,
                                                                  arg2.address);

            //then
            assertThat(result).isEqualTo((byte) 123);
            assertThat(arg0.dref()
                           .field2()
                           .dref(0)).isEqualTo(567);
            assertThat(arg0.dref()
                           .field2()
                           .dref(1)).isEqualTo(345);
            assertThat(arg0.dref()
                           .field3().address).isEqualTo(field3.address);
        }
    }

    public byte function(@Ptr(StructType.class) final long arg0,
                         @Unsigned final int arg1,
                         @ByVal(TestStruct.class) final long arg2) {

        final Pointer<TestStruct> testStructByRef = Pointer.wrap(TestStruct.class,
                                                                 arg0);
        final TestStruct testStructByVal = Pointer.wrap(TestStruct.class,
                                                        arg2)
                                                  .dref();

        testStructByRef.dref()
                       .field2()
                       .writei(0,
                               arg1);
        testStructByRef.dref()
                       .field2()
                       .writei(1,
                               (int) testStructByVal.field1());

        testStructByRef.dref()
                       .field3(testStructByVal.field3());

        return testStructByVal.field0();
    }

    @Test
    public void charTestFunctionPointerFromJava() {}

    @Test
    public void unsignedCharTestFunctionPointerFromJava() {}

    @Test
    public void shortTestFunctionPointerFromJava() {}

    @Test
    public void unsignedShortTestFunctionPointerFromJava() {}

    @Test
    public void intTestFunctionPointerFromJava() {}

    @Test
    public void unsignedIntTestFunctionPointerFromJava() {}

    @Test
    public void longTestFunctionPointerFromJava() {}

    @Test
    public void unsignedLongTestFunctionPointerFromJava() {}

    @Test
    public void longLongTestFunctionPointerFromJava() {}

    @Test
    public void unsignedLongLongTestFunctionPointerFromJava() {}

    @Test
    public void floatTestFunctionPointerFromJava() {}

    @Test
    public void doubleTestFunctionPointerFromJava() {}

    @Test
    public void pointerTestFunctionPointerFromJava() {}

    @Test
    public void structTestFunctionPointerFromJava() {}

    @Test
    public void structTest2FunctionPointerFromJava() {}

    @Test
    public void unionTestFunctionPointerFromJava() {}

    @Test
    public void unionTest2FunctionPointerFromJava() {}


    public byte charTestInJava(byte value) {
        return value;
    }

    @Unsigned
    public byte unsignedCharTestInJava(@Unsigned byte value) {
        return value;
    }

    public short shortTestInJava(short value) {
        return value;
    }

    @Unsigned
    public short unsignedShortTestInJava(@Unsigned short value) {
        return value;
    }

    public int intTestInJava(int value) {
        return value;
    }

    @Unsigned
    public int unsignedIntTestInJava(@Unsigned int value) {
        return value;
    }

    public long longTestInJava(long value) {
        return value;
    }

    @Unsigned
    public long unsignedLongTestInJava(@Unsigned long value) {
        return value;
    }

    @Lng
    public long longLongTestInJava(@Lng long value) {
        return value;
    }

    @Unsigned
    @Lng
    public long unsignedLongLongTestInJava(@Unsigned @Lng long value) {
        return value;
    }

    public float floatTestInJava(float value) {
        return value;
    }

    public double doubleTestInJava(double value) {
        return value;
    }

    @Ptr
    public long pointerTestInJava(@Ptr long value) {
        return value;
    }


    @ByVal(TestStruct.class)
    public static long structTestInJava(@Ptr(TestStruct.class) long tst,
                                        byte field0,
                                        @Unsigned short field1,
                                        @Ptr(int.class) long field2,
                                        @Ptr(int.class) long field3,
                                        @Lng long embedded_field0,
                                        float embedded_field1) {
        return 0;
    }

    @Ptr(TestStruct.class)
    public static long structTest2InJava(@ByVal(TestStruct.class) long tst,
                                         byte field0,
                                         @Unsigned short field1,
                                         @Ptr(int.class) long field2,
                                         @Ptr(int.class) long field3,
                                         @Lng long embedded_field0,
                                         float embedded_field1) {
        return 0;
    }

    @ByVal(TestUnion.class)
    public static long unionTestInJava(@Ptr(TestUnion.class) long tst,
                                       int field0,
                                       float field1) {
        return 0;
    }

    @Ptr(TestUnion.class)
    public static long unionTest2InJava(@ByVal(TestUnion.class) long tst,
                                        int field0) {
        return 0;
    }

    @Test
    public void charTestFunctionPointerFromC() {}

    @Test
    public void unsignedCharTestFunctionPointerFromC() {}

    @Test
    public void shortTestFunctionPointerFromC() {}

    @Test
    public void unsignedShortTestFunctionPointerFromC() {}

    @Test
    public void intTestFunctionPointerFromC() {}

    @Test
    public void unsignedIntTestFunctionPointerFromC() {}

    @Test
    public void longTestFunctionPointerFromC() {}

    @Test
    public void unsignedLongTestFunctionPointerFromC() {}

    @Test
    public void longLongTestFunctionPointerFromC() {}

    @Test
    public void unsignedLongLongTestFunctionPointerFromC() {}

    @Test
    public void floatTestFunctionPointerFromC() {}

    @Test
    public void doubleTestFunctionPointerFromC() {}

    @Test
    public void pointerTestFunctionPointerFromC() {}

    @Test
    public void structTestFunctionPointerFromC() {}

    @Test
    public void structTest2FunctionPointerFromC() {}

    @Test
    public void unionTestFunctionPointerFromC() {}

    @Test
    public void unionTest2FunctionPointerFromC() {}
}
