package com.github.zubnix.jaccall;


import com.github.zubnix.libtest.FooFunc;
import com.github.zubnix.libtest.PointerCharFunc;
import com.github.zubnix.libtest.PointerDoubleFunc;
import com.github.zubnix.libtest.PointerFloatFunc;
import com.github.zubnix.libtest.PointerFooFunc;
import com.github.zubnix.libtest.PointerIntFunc;
import com.github.zubnix.libtest.PointerLongFunc;
import com.github.zubnix.libtest.PointerLongLongFunc;
import com.github.zubnix.libtest.PointerShortFunc;
import com.github.zubnix.libtest.PointerUnsignedCharFunc;
import com.github.zubnix.libtest.PointerUnsignedIntFunc;
import com.github.zubnix.libtest.PointerUnsignedLongFunc;
import com.github.zubnix.libtest.PointerUnsignedLongLongFunc;
import com.github.zubnix.libtest.PointerUnsignedShortFunc;
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

        final long           funcPtrAddr     = new Testing().getFunctionPointerTest();
        final PointerFooFunc pointerTestFunc = PointerFooFunc.wrapFunc(funcPtrAddr);

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

        final PointerFooFunc pointerTestFunc = PointerFooFunc.nref(
                new FooFunc() {
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
    public void testCharFunctionPointerFromJava() {
        //given
        final PointerCharFunc pointerCharTest = PointerCharFunc.nref(new Testing.CharFunc() {
            @Override
            public byte $(final byte value) {
                return charTest(value);
            }
        });

        final byte value = 123;

        //when
        final byte retVal = JNITestUtil.execCharTest(pointerCharTest.address,
                                                     value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public byte charTest(final byte value) {
        return value;
    }

    @Test
    public void testUnsignedCharFunctionPointerFromJava() {
        //given
        final PointerUnsignedCharFunc pointerUnsignedCharTest = PointerUnsignedCharFunc.nref(new Testing.UnsignedCharFunc() {
            @Override
            public byte $(final byte value) {
                return unsignedCharTest(value);
            }
        });

        final byte value = 123;

        //when
        final byte retVal = JNITestUtil.execUnsignedCharTest(pointerUnsignedCharTest.address,
                                                             value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public byte unsignedCharTest(final byte value) {
        return value;
    }

    @Test
    public void testShortFunctionPointerFromJava() {
        //given
        final PointerShortFunc pointerShortTest = PointerShortFunc.nref(new Testing.ShortFunc() {
            @Override
            public short $(final short value) {
                return shortTest(value);
            }
        });

        final short value = 32536;

        //when
        final short retVal = JNITestUtil.execShortTest(pointerShortTest.address,
                                                       value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public short shortTest(final short value) {
        return value;
    }

    @Test
    public void testUnsignedShortFunctionPointerFromJava() {
        //given
        final PointerUnsignedShortFunc pointerUnsignedShortTest = PointerUnsignedShortFunc.nref(new Testing.UnsignedShortFunc() {
            @Override
            public short $(final short value) {
                return unsignedShortTest(value);
            }
        });

        final short value = 32536;

        //when
        final short retVal = JNITestUtil.execUnsignedShortTest(pointerUnsignedShortTest.address,
                                                               value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public short unsignedShortTest(final short value) {
        return value;
    }

    @Test
    public void testIntFunctionPointerFromJava() {
        //given
        final PointerIntFunc pointerIntTest = PointerIntFunc.nref(new Testing.IntFunc() {
            @Override
            public int $(final int value) {
                return intTest(value);
            }
        });

        final int value = 325364564;

        //when
        final int retVal = JNITestUtil.execIntTest(pointerIntTest.address,
                                                   value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public int intTest(final int value) { return value; }

    @Test
    public void testUnsignedIntFunctionPointerFromJava() {
        //given
        final PointerUnsignedIntFunc pointerUnsignedIntTest = PointerUnsignedIntFunc.nref(new Testing.UnsignedIntFunc() {
            @Override
            public int $(final int value) {
                return unsignedIntTest(value);
            }
        });

        final int value = 325364564;

        //when
        final int retVal = JNITestUtil.execUnsignedIntTest(pointerUnsignedIntTest.address,
                                                           value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public int unsignedIntTest(final int value) { return value; }


    @Test
    public void testLongFunctionPointerFromJava() {
        //given
        final PointerLongFunc pointerLongTest = PointerLongFunc.nref(new Testing.LongFunc() {
            @Override
            public long $(final long value) {
                return longTest(value);
            }
        });

        final int value = 325364564;

        //when
        final long retVal = JNITestUtil.execLongTest(pointerLongTest.address,
                                                     value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public long longTest(final long value) { return value; }

    @Test
    public void testUnsignedLongFunctionPointerFromJava() {
        //given
        final PointerUnsignedLongFunc pointerUnsignedLongTest = PointerUnsignedLongFunc.nref(new Testing.UnsignedLongFunc() {
            @Override
            public long $(final long value) {
                return unsignedLongTest(value);
            }
        });

        final int value = 325364564;

        //when
        final long retVal = JNITestUtil.execUnsignedLongTest(pointerUnsignedLongTest.address,
                                                             value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public long unsignedLongTest(final long value) { return value; }


    @Test
    public void testLongLongFunctionPointerFromJava() {
        //given
        final PointerLongLongFunc pointerLongTest = PointerLongLongFunc.nref(new Testing.LongLongFunc() {
            @Override
            public long $(final long value) {
                return longLongTest(value);
            }
        });

        final long value = 3253645644545632145L;

        //when
        final long retVal = JNITestUtil.execLongLongTest(pointerLongTest.address,
                                                         value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public long longLongTest(final long value) { return value; }

    @Test
    public void testUnsignedLongLongFunctionPointerFromJava() {
        //given
        final PointerUnsignedLongLongFunc pointerLongTest = PointerUnsignedLongLongFunc.nref(new Testing.UnsignedLongLongFunc() {
            @Override
            public long $(final long value) {
                return unsignedLongLongTest(value);
            }
        });

        final long value = 3253645644545632145L;

        //when
        final long retVal = JNITestUtil.execUnsignedLongLongTest(pointerLongTest.address,
                                                                 value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public long unsignedLongLongTest(final long value) { return value; }


    @Test
    public void testFloatFunctionPointerFromJava() {
        //given
        final PointerFloatFunc pointerLongTest = PointerFloatFunc.nref(new Testing.FloatFunc() {
            @Override
            public float $(final float value) {
                return floatTest(value);
            }
        });

        final float value = 325364564.456789F;

        //when
        final float retVal = JNITestUtil.execFloatTest(pointerLongTest.address,
                                                       value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public float floatTest(final float value) { return value; }

    @Test
    public void testDoubleFunctionPointerFromJava() {
        //given
        final PointerDoubleFunc pointerLongTest = PointerDoubleFunc.nref(new Testing.DoubleFunc() {
            @Override
            public double $(final double value) {
                return doubleTest(value);
            }
        });

        final double value = 325364564753159.456789159753D;

        //when
        final double retVal = JNITestUtil.execDoubleTest(pointerLongTest.address,
                                                         value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public double doubleTest(final double value) { return value; }

    @Test
    public void testPointerFunctionPointerFromJava() {}

    @Test
    public void testStructFunctionPointerFromJava() {}

    @Test
    public void testStruct2FunctionPointerFromJava() {}

    @Test
    public void testUnionFunctionPointerFromJava() {}

    @Test
    public void testUnion2FunctionPointerFromJava() {}


    @ByVal(TestStruct.class)
    public static long structTestInJava(@Ptr(TestStruct.class) final long tst,
                                        final byte field0,
                                        @Unsigned final short field1,
                                        @Ptr(int.class) final long field2,
                                        @Ptr(int.class) final long field3,
                                        @Lng final long embedded_field0,
                                        final float embedded_field1) {
        return 0;
    }

    @Ptr(TestStruct.class)
    public static long structTest2InJava(@ByVal(TestStruct.class) final long tst,
                                         final byte field0,
                                         @Unsigned final short field1,
                                         @Ptr(int.class) final long field2,
                                         @Ptr(int.class) final long field3,
                                         @Lng final long embedded_field0,
                                         final float embedded_field1) {
        return 0;
    }

    @ByVal(TestUnion.class)
    public static long unionTestInJava(@Ptr(TestUnion.class) final long tst,
                                       final int field0,
                                       final float field1) {
        return 0;
    }

    @Ptr(TestUnion.class)
    public static long unionTest2InJava(@ByVal(TestUnion.class) final long tst,
                                        final int field0) {
        return 0;
    }

    @Test
    public void testCharFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long            charTestFunctionPointer = new Testing().charTestFunctionPointer();
        final PointerCharFunc pointerCharTest         = PointerCharFunc.wrapFunc(charTestFunctionPointer);

        final byte value = 123;

        //when
        final byte retVal = pointerCharTest.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedCharFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long                    unsignedCharTestFunctionPointer = new Testing().unsignedCharTestFunctionPointer();
        final PointerUnsignedCharFunc pointerCharTest                 = PointerUnsignedCharFunc.wrapFunc(unsignedCharTestFunctionPointer);

        final byte value = 123;

        //when
        final byte retVal = pointerCharTest.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testShortFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long             shortTestFunctionPointer = new Testing().shortTestFunctionPointer();
        final PointerShortFunc pointerShortTest         = PointerShortFunc.wrapFunc(shortTestFunctionPointer);

        final short value = 32536;

        //when
        final short retVal = pointerShortTest.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedShortFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long                     unsignedShortTestFunctionPointer = new Testing().unsignedShortTestFunctionPointer();
        final PointerUnsignedShortFunc pointerUnsignedShortTest         = PointerUnsignedShortFunc.wrapFunc(unsignedShortTestFunctionPointer);

        final short value = 32536;

        //when
        final short retVal = pointerUnsignedShortTest.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testIntFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long           intTestFunctionPointer = new Testing().intTestFunctionPointer();
        final PointerIntFunc pointerIntTest         = PointerIntFunc.wrapFunc(intTestFunctionPointer);

        final int value = 32536987;

        //when
        final int retVal = pointerIntTest.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedIntFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long                   unsignedIntTestFunctionPointer = new Testing().unsignedIntTestFunctionPointer();
        final PointerUnsignedIntFunc pointerIntTest                 = PointerUnsignedIntFunc.wrapFunc(unsignedIntTestFunctionPointer);

        final int value = 32536987;

        //when
        final int retVal = pointerIntTest.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testLongFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long            longTestFunctionPointer = new Testing().longTestFunctionPointer();
        final PointerLongFunc pointerLongTest         = PointerLongFunc.wrapFunc(longTestFunctionPointer);

        final int value = 32536456;

        //when
        final long retVal = pointerLongTest.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedLongFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long                    longTestFunctionPointer = new Testing().unsignedLongTestFunctionPointer();
        final PointerUnsignedLongFunc pointerLongTest         = PointerUnsignedLongFunc.wrapFunc(longTestFunctionPointer);

        final int value = 32536456;

        //when

        final long retVal = pointerLongTest.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testLongLongFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long                longLongTestFunctionPointer = new Testing().longLongTestFunctionPointer();
        final PointerLongLongFunc pointerLongLongFunc         = PointerLongLongFunc.wrapFunc(longLongTestFunctionPointer);

        final long value = 325364567789456L;

        //when
        final long retVal = pointerLongLongFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedLongLongFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long                        unsignedLongLongTestFunctionPointer = new Testing().unsignedLongLongTestFunctionPointer();
        final PointerUnsignedLongLongFunc pointerUnsignedLongLongTest         = PointerUnsignedLongLongFunc.wrapFunc(unsignedLongLongTestFunctionPointer);

        final long value = 325364567789456L;

        //when
        final long retVal = pointerUnsignedLongLongTest.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testFloatFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long             floatTestFunctionPointer = new Testing().floatTestFunctionPointer();
        final PointerFloatFunc pointerFloatTest         = PointerFloatFunc.wrapFunc(floatTestFunctionPointer);

        final float value = 32536456.123456F;

        //when
        final float retVal = pointerFloatTest.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testDoubleFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long              doubleTestFunctionPointer = new Testing().doubleTestFunctionPointer();
        final PointerDoubleFunc pointerFloatTest          = PointerDoubleFunc.wrapFunc(doubleTestFunctionPointer);

        final double value = 32536456159753.12345615975D;

        //when
        final double retVal = pointerFloatTest.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testPointerFunctionPointerFromC() {}

    @Test
    public void testStructFunctionPointerFromC() {}

    @Test
    public void testStruct2FunctionPointerFromC() {}

    @Test
    public void testUnionFunctionPointerFromC() {}

    @Test
    public void testUnion2FunctionPointerFromC() {}
}
