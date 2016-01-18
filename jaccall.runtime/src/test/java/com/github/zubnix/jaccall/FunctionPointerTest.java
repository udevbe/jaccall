package com.github.zubnix.jaccall;


import com.github.zubnix.libtest.FooFunc;
import com.github.zubnix.libtest.PointerCharFunc;
import com.github.zubnix.libtest.PointerDoubleFunc;
import com.github.zubnix.libtest.PointerFloatFunc;
import com.github.zubnix.libtest.PointerFooFunc;
import com.github.zubnix.libtest.PointerIntFunc;
import com.github.zubnix.libtest.PointerLongFunc;
import com.github.zubnix.libtest.PointerLongLongFunc;
import com.github.zubnix.libtest.PointerPointerFunc;
import com.github.zubnix.libtest.PointerShortFunc;
import com.github.zubnix.libtest.PointerStructFunc;
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

import static com.github.zubnix.jaccall.Pointer.malloc;
import static com.github.zubnix.jaccall.Pointer.nref;
import static com.github.zubnix.jaccall.Pointer.wrap;
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
        final PointerUnsignedLongFunc pointerUnsignedLongFunc = PointerUnsignedLongFunc.nref(new Testing.UnsignedLongFunc() {
            @Override
            public long $(final long value) {
                return unsignedLongTest(value);
            }
        });

        final int value = 325364564;

        //when
        final long retVal = JNITestUtil.execUnsignedLongTest(pointerUnsignedLongFunc.address,
                                                             value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public long unsignedLongTest(final long value) { return value; }


    @Test
    public void testLongLongFunctionPointerFromJava() {
        //given
        final PointerLongLongFunc pointerLongLongFunc = PointerLongLongFunc.nref(new Testing.LongLongFunc() {
            @Override
            public long $(final long value) {
                return longLongTest(value);
            }
        });

        final long value = 3253645644545632145L;

        //when
        final long retVal = JNITestUtil.execLongLongTest(pointerLongLongFunc.address,
                                                         value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public long longLongTest(final long value) { return value; }

    @Test
    public void testUnsignedLongLongFunctionPointerFromJava() {
        //given
        final PointerUnsignedLongLongFunc pointerUnsignedLongLongFunc = PointerUnsignedLongLongFunc.nref(new Testing.UnsignedLongLongFunc() {
            @Override
            public long $(final long value) {
                return unsignedLongLongTest(value);
            }
        });

        final long value = 3253645644545632145L;

        //when
        final long retVal = JNITestUtil.execUnsignedLongLongTest(pointerUnsignedLongLongFunc.address,
                                                                 value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public long unsignedLongLongTest(final long value) { return value; }


    @Test
    public void testFloatFunctionPointerFromJava() {
        //given
        final PointerFloatFunc pointerFloatFunc = PointerFloatFunc.nref(new Testing.FloatFunc() {
            @Override
            public float $(final float value) {
                return floatTest(value);
            }
        });

        final float value = 325364564.456789F;

        //when
        final float retVal = JNITestUtil.execFloatTest(pointerFloatFunc.address,
                                                       value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public float floatTest(final float value) { return value; }

    @Test
    public void testDoubleFunctionPointerFromJava() {
        //given
        final PointerDoubleFunc pointerDoubleFunc = PointerDoubleFunc.nref(new Testing.DoubleFunc() {
            @Override
            public double $(final double value) {
                return doubleTest(value);
            }
        });

        final double value = 325364564753159.456789159753D;

        //when
        final double retVal = JNITestUtil.execDoubleTest(pointerDoubleFunc.address,
                                                         value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public double doubleTest(final double value) { return value; }

    @Test
    public void testPointerFunctionPointerFromJava() {
        //given
        final PointerPointerFunc pointerPointerFunc = PointerPointerFunc.nref(new Testing.PointerFunc() {
            @Override
            public long $(final long value) {
                return pointerTest(value);
            }
        });

        final int value = 325364564;

        //when
        final long retVal = JNITestUtil.execPointerTest(pointerPointerFunc.address,
                                                        value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    public long pointerTest(final long value) { return value; }

    @Test
    public void testStructFunctionPointerFromJava() {
        //given
        final PointerStructFunc pointerStructFunc = PointerStructFunc.nref(new Testing.StructFunc() {
            @Override
            public long $(final long tst,
                          final byte field0,
                          final short field1,
                          final long field2,
                          final long field3,
                          final long embedded_field0,
                          final float embedded_field1) {
                return structTest(tst,
                                  field0,
                                  field1,
                                  field2,
                                  field3,
                                  embedded_field0,
                                  embedded_field1);
            }
        });

        final Pointer<TestStruct> testStructPointer = malloc(TestStruct.SIZE).castp(TestStruct.class);
        final TestStruct          testStruct        = testStructPointer.dref();

        final byte             field0 = 10;
        final short            field1 = 20;
        final Pointer<Integer> field3 = nref(40);

        testStruct.field0(field0);
        testStruct.field1(field1);
        testStruct.field2()
                  .writei(1,
                          1);
        testStruct.field2()
                  .writei(2,
                          11);
        testStruct.field2()
                  .writei(3,
                          111);
        testStruct.field3(field3);

        //when
        try (Pointer<TestStruct> tst = testStructPointer;
             Pointer<Integer> intp = nref(44)) {

            final byte newField0 = 'a';
            final short newField1 = 22;
            final int newField2_0 = 123;
            final int newField2_1 = 456;
            final int newField2_2 = 789;

            final Pointer<Integer> newField2 = nref(newField2_0,
                                                    newField2_1,
                                                    newField2_2);

            final Pointer<Integer> newField3 = intp;
            final long embedded_field0 = 1234567890L;
            final float embedded_field1 = 9876543.21F;

            final Pointer<TestStruct> testStructByValue = wrap(TestStruct.class,
                                                               JNITestUtil.execStructTest(pointerStructFunc.address,
                                                                                          tst.address,
                                                                                          newField0,
                                                                                          newField1,
                                                                                          newField2.address,
                                                                                          newField3.address,
                                                                                          embedded_field0,
                                                                                          embedded_field1));

            //then
            final TestStruct testStruct1 = testStructByValue.dref();
            assertThat(testStruct1.field0()).isEqualTo(newField0);
            assertThat(testStruct1.field1()).isEqualTo(newField1);
            assertThat(testStruct1.field2()
                                  .dref(0)).isEqualTo(newField2_0);
            assertThat(testStruct1.field2()
                                  .dref(1)).isEqualTo(newField2_1);
            assertThat(testStruct1.field2()
                                  .dref(2)).isEqualTo(newField2_2);
            assertThat(testStruct1.field3()).isEqualTo(newField3);

            assertThat(testStruct.field0()).isEqualTo(newField0);
            assertThat(testStruct.field1()).isEqualTo(newField1);
            assertThat(testStruct.field2()
                                 .dref(0)).isEqualTo(newField2_0);
            assertThat(testStruct.field2()
                                 .dref(1)).isEqualTo(newField2_1);
            assertThat(testStruct.field2()
                                 .dref(2)).isEqualTo(newField2_2);
            assertThat(testStruct.field3()).isEqualTo(newField3);

            testStructByValue.close();
        }
    }

    public long structTest(final long tstPointer,
                           final byte field0,
                           final short field1,
                           final long field2Array,
                           final long field3,
                           final long embedded_field0,
                           final float embedded_field1) {
        //@formatter:off
        final Pointer<TestStruct> tst = Pointer.wrap(TestStruct.class, tstPointer);

        tst.dref().field0(field0);
        tst.dref().field1(field1);
        final Pointer<Integer> field2 = Pointer.wrap(Integer.class, field2Array);
        tst.dref().field2().writei(0, field2.dref(0));
        tst.dref().field2().writei(1, field2.dref(1));
        tst.dref().field2().writei(2, field2.dref(2));
        tst.dref().field3(Pointer.wrap(Integer.class, field3));
        tst.dref().field4().field0(embedded_field0);
        tst.dref().field4().field1(embedded_field1);

        final TestStruct someTest = new TestStruct();
        someTest.field0(tst.dref().field0());
        someTest.field1(tst.dref().field1());
        someTest.field2().writei(0,tst.dref().field2().dref(0));
        someTest.field2().writei(1,tst.dref().field2().dref(1));
        someTest.field2().writei(2,tst.dref().field2().dref(2));
        someTest.field3(tst.dref().field3());
        someTest.field4().field0(tst.dref().field4().field0());
        someTest.field4().field1(tst.dref().field4().field1());

        return Pointer.nref(someTest).address;
        //@formatter:on
    }

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

        final long            pointer         = new Testing().charTestFunctionPointer();
        final PointerCharFunc pointerCharFunc = PointerCharFunc.wrapFunc(pointer);

        final byte value = 123;

        //when
        final byte retVal = pointerCharFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedCharFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long                    pointer                 = new Testing().unsignedCharTestFunctionPointer();
        final PointerUnsignedCharFunc pointerUnsignedCharFunc = PointerUnsignedCharFunc.wrapFunc(pointer);

        final byte value = 123;

        //when
        final byte retVal = pointerUnsignedCharFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testShortFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long             pointer          = new Testing().shortTestFunctionPointer();
        final PointerShortFunc pointerShortFunc = PointerShortFunc.wrapFunc(pointer);

        final short value = 32536;

        //when
        final short retVal = pointerShortFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedShortFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long                     pointer                  = new Testing().unsignedShortTestFunctionPointer();
        final PointerUnsignedShortFunc pointerUnsignedShortFunc = PointerUnsignedShortFunc.wrapFunc(pointer);

        final short value = 32536;

        //when
        final short retVal = pointerUnsignedShortFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testIntFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long           pointer        = new Testing().intTestFunctionPointer();
        final PointerIntFunc pointerIntFunc = PointerIntFunc.wrapFunc(pointer);

        final int value = 32536987;

        //when
        final int retVal = pointerIntFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedIntFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long                   pointer                = new Testing().unsignedIntTestFunctionPointer();
        final PointerUnsignedIntFunc pointerUnsignedIntFunc = PointerUnsignedIntFunc.wrapFunc(pointer);

        final int value = 32536987;

        //when
        final int retVal = pointerUnsignedIntFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testLongFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long            pointer         = new Testing().longTestFunctionPointer();
        final PointerLongFunc pointerLongFunc = PointerLongFunc.wrapFunc(pointer);

        final int value = 32536456;

        //when
        final long retVal = pointerLongFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedLongFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long                    pointer                 = new Testing().unsignedLongTestFunctionPointer();
        final PointerUnsignedLongFunc pointerUnsignedLongFunc = PointerUnsignedLongFunc.wrapFunc(pointer);

        final int value = 32536456;

        //when

        final long retVal = pointerUnsignedLongFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testLongLongFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long                pointer                               = new Testing().longLongTestFunctionPointer();
        final PointerLongLongFunc poinpointerLongLongFuncerLongLongFunc = PointerLongLongFunc.wrapFunc(pointer);

        final long value = 325364567789456L;

        //when
        final long retVal = poinpointerLongLongFuncerLongLongFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedLongLongFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long                        pointer                     = new Testing().unsignedLongLongTestFunctionPointer();
        final PointerUnsignedLongLongFunc pointerUnsignedLongLongFunc = PointerUnsignedLongLongFunc.wrapFunc(pointer);

        final long value = 325364567789456L;

        //when
        final long retVal = pointerUnsignedLongLongFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testFloatFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long             pointer          = new Testing().floatTestFunctionPointer();
        final PointerFloatFunc pointerFloatFunc = PointerFloatFunc.wrapFunc(pointer);

        final float value = 32536456.123456F;

        //when
        final float retVal = pointerFloatFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testDoubleFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long              pointer           = new Testing().doubleTestFunctionPointer();
        final PointerDoubleFunc pointerDoubleFunc = PointerDoubleFunc.wrapFunc(pointer);

        final double value = 32536456159753.12345615975D;

        //when
        final double retVal = pointerDoubleFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testPointerFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long               pointer            = new Testing().pointerTestFunctionPointer();
        final PointerPointerFunc pointerPointerFunc = PointerPointerFunc.wrapFunc(pointer);

        final int value = 32536456;

        //when
        final long retVal = pointerPointerFunc.$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testStructFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long              pointer           = Testing.structTestFunctionPointer();
        final PointerStructFunc pointerStructFunc = PointerStructFunc.wrapFunc(pointer);

        final Pointer<TestStruct> testStructPointer = malloc(TestStruct.SIZE).castp(TestStruct.class);
        final TestStruct          testStruct        = testStructPointer.dref();

        final byte             field0 = 10;
        final short            field1 = 20;
        final Pointer<Integer> field3 = nref(40);

        testStruct.field0(field0);
        testStruct.field1(field1);
        testStruct.field2()
                  .writei(1,
                          1);
        testStruct.field2()
                  .writei(2,
                          11);
        testStruct.field2()
                  .writei(3,
                          111);
        testStruct.field3(field3);

        //when
        try (Pointer<TestStruct> tst = testStructPointer;
             Pointer<Integer> intp = nref(44)) {

            final byte newField0 = 'a';
            final short newField1 = 22;
            final int newField2_0 = 123;
            final int newField2_1 = 456;
            final int newField2_2 = 789;

            final Pointer<Integer> newField2 = nref(newField2_0,
                                                    newField2_1,
                                                    newField2_2);

            final Pointer<Integer> newField3 = intp;
            final long embedded_field0 = 1234567890L;
            final float embedded_field1 = 9876543.21F;

            final Pointer<TestStruct> testStructByValue = wrap(TestStruct.class,
                                                               pointerStructFunc.$(tst.address,
                                                                                   newField0,
                                                                                   newField1,
                                                                                   newField2.address,
                                                                                   newField3.address,
                                                                                   embedded_field0,
                                                                                   embedded_field1));

            //then
            final TestStruct testStruct1 = testStructByValue.dref();
            assertThat(testStruct1.field0()).isEqualTo(newField0);
            assertThat(testStruct1.field1()).isEqualTo(newField1);
            assertThat(testStruct1.field2()
                                  .dref(0)).isEqualTo(newField2_0);
            assertThat(testStruct1.field2()
                                  .dref(1)).isEqualTo(newField2_1);
            assertThat(testStruct1.field2()
                                  .dref(2)).isEqualTo(newField2_2);
            assertThat(testStruct1.field3()).isEqualTo(newField3);

            assertThat(testStruct.field0()).isEqualTo(newField0);
            assertThat(testStruct.field1()).isEqualTo(newField1);
            assertThat(testStruct.field2()
                                 .dref(0)).isEqualTo(newField2_0);
            assertThat(testStruct.field2()
                                 .dref(1)).isEqualTo(newField2_1);
            assertThat(testStruct.field2()
                                 .dref(2)).isEqualTo(newField2_2);
            assertThat(testStruct.field3()).isEqualTo(newField3);

            testStructByValue.close();
        }
    }

    @Test
    public void testStruct2FunctionPointerFromC() {}

    @Test
    public void testUnionFunctionPointerFromC() {}

    @Test
    public void testUnion2FunctionPointerFromC() {}
}
