package com.github.zubnix.jaccall;


import com.github.zubnix.libtest.CharFunc;
import com.github.zubnix.libtest.DoubleFunc;
import com.github.zubnix.libtest.FloatFunc;
import com.github.zubnix.libtest.FooFunc;
import com.github.zubnix.libtest.IntFunc;
import com.github.zubnix.libtest.LongFunc;
import com.github.zubnix.libtest.LongLongFunc;
import com.github.zubnix.libtest.PointerDoubleFunc;
import com.github.zubnix.libtest.PointerFloatFunc;
import com.github.zubnix.libtest.PointerFooFunc;
import com.github.zubnix.libtest.PointerFunc;
import com.github.zubnix.libtest.PointerIntFunc;
import com.github.zubnix.libtest.PointerLongFunc;
import com.github.zubnix.libtest.ShortFunc;
import com.github.zubnix.libtest.StructFunc;
import com.github.zubnix.libtest.StructFunc2;
import com.github.zubnix.libtest.TestStruct;
import com.github.zubnix.libtest.TestUnion;
import com.github.zubnix.libtest.Testing;
import com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols;
import com.github.zubnix.libtest.UnionFunc;
import com.github.zubnix.libtest.UnionFunc2;
import com.github.zubnix.libtest.UnsignedCharFunc;
import com.github.zubnix.libtest.UnsignedIntFunc;
import com.github.zubnix.libtest.UnsignedLongFunc;
import com.github.zubnix.libtest.UnsignedLongLongFunc;
import com.github.zubnix.libtest.UnsignedShortFunc;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.github.zubnix.jaccall.Pointer.malloc;
import static com.github.zubnix.jaccall.Pointer.ref;
import static com.github.zubnix.jaccall.Pointer.wrap;
import static com.github.zubnix.jaccall.Size.sizeof;
import static com.github.zubnix.libtest.PointerCharFunc.nref;
import static com.github.zubnix.libtest.PointerDoubleFunc.nref;
import static com.github.zubnix.libtest.PointerFloatFunc.nref;
import static com.github.zubnix.libtest.PointerIntFunc.nref;
import static com.github.zubnix.libtest.PointerLongFunc.nref;
import static com.github.zubnix.libtest.PointerLongLongFunc.nref;
import static com.github.zubnix.libtest.PointerPointerFunc.nref;
import static com.github.zubnix.libtest.PointerShortFunc.nref;
import static com.github.zubnix.libtest.PointerStructFunc.nref;
import static com.github.zubnix.libtest.PointerStructFunc2.nref;
import static com.github.zubnix.libtest.PointerUnionFunc.nref;
import static com.github.zubnix.libtest.PointerUnionFunc2.nref;
import static com.github.zubnix.libtest.PointerUnsignedCharFunc.nref;
import static com.github.zubnix.libtest.PointerUnsignedIntFunc.nref;
import static com.github.zubnix.libtest.PointerUnsignedLongFunc.nref;
import static com.github.zubnix.libtest.PointerUnsignedLongLongFunc.nref;
import static com.github.zubnix.libtest.PointerUnsignedShortFunc.nref;
import static com.google.common.truth.Truth.assertThat;

public class FunctorTest {

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

        final long funcPtrAddr = new Testing().getFunctionPointerTest();
        final Pointer<FooFunc> pointerTestFunc = wrap(FooFunc.class,
                                                      funcPtrAddr);

        try (final Pointer<TestStruct> arg0 = malloc(sizeof(TestStruct.SIZE))
                .castp(TestStruct.class);
             final Pointer<TestStruct> arg2 = malloc(sizeof(TestStruct.SIZE))
                     .castp(TestStruct.class);
             final Pointer<Integer> field3 = malloc(sizeof((Integer) null))
                     .castp(Integer.class)) {

            arg2.dref()
                .field0((byte) 123);
            arg2.dref()
                .field1((short) 345);
            arg2.dref()
                .field3(field3);

            //when
            final byte result = pointerTestFunc.dref()
                                               .$(arg0.address,
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

        try (final Pointer<TestStruct> arg0 = malloc(sizeof(TestStruct.SIZE)).castp(TestStruct.class);
             final Pointer<TestStruct> arg2 = malloc(sizeof(TestStruct.SIZE)).castp(TestStruct.class);
             final Pointer<Integer> field3 = malloc(sizeof((Integer) null)).castp(Integer.class)) {

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

        final Pointer<TestStruct> testStructByRef = wrap(TestStruct.class,
                                                         arg0);
        final TestStruct testStructByVal = wrap(TestStruct.class,
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
        final Pointer<CharFunc> pointerCharTest = nref(new CharFunc() {
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
        final Pointer<UnsignedCharFunc> pointerUnsignedCharTest = nref(new UnsignedCharFunc() {
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
        final Pointer<ShortFunc> pointerShortTest = nref(new ShortFunc() {
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
        final Pointer<UnsignedShortFunc> pointerUnsignedShortTest = nref(new UnsignedShortFunc() {
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
        final PointerIntFunc pointerIntTest = nref(new IntFunc() {
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
        final Pointer<UnsignedIntFunc> pointerUnsignedIntTest = nref(new UnsignedIntFunc() {
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
        final PointerLongFunc pointerLongTest = nref(new LongFunc() {
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
        final Pointer<UnsignedLongFunc> pointerUnsignedLongFunc = nref(new UnsignedLongFunc() {
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
        final Pointer<LongLongFunc> pointerLongLongFunc = nref(new LongLongFunc() {
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
        final Pointer<UnsignedLongLongFunc> pointerUnsignedLongLongFunc = nref(new UnsignedLongLongFunc() {
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
        final PointerFloatFunc pointerFloatFunc = nref(new FloatFunc() {
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
        final PointerDoubleFunc pointerDoubleFunc = nref(new DoubleFunc() {
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
        final Pointer<PointerFunc> pointerPointerFunc = nref(new PointerFunc() {
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
    public void testStructFunctionPointerFromJava() throws InterruptedException {
        //given
        final Pointer<StructFunc> pointerStructFunc = nref(new StructFunc() {
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
        final Pointer<Integer> field3 = Pointer.nref(40);

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
             Pointer<Integer> intp = Pointer.nref(44)) {

            final byte  newField0   = 'a';
            final short newField1   = 22;
            final int   newField2_0 = 123;
            final int   newField2_1 = 456;
            final int   newField2_2 = 789;

            final Pointer<Integer> newField2 = Pointer.nref(newField2_0,
                                                            newField2_1,
                                                            newField2_2);

            final Pointer<Integer> newField3       = intp;
            final long             embedded_field0 = 1234567890L;
            final float            embedded_field1 = 9876543.21F;

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
        final Pointer<TestStruct> tst = wrap(TestStruct.class, tstPointer);

        tst.dref().field0(field0);
        tst.dref().field1(field1);
        final Pointer<Integer> field2 = wrap(Integer.class, field2Array);
        tst.dref().field2().writei(0, field2.dref(0));
        tst.dref().field2().writei(1, field2.dref(1));
        tst.dref().field2().writei(2, field2.dref(2));
        tst.dref().field3(wrap(Integer.class, field3));
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

        return ref(someTest).address;
        //@formatter:on
    }

    @Test
    public void testStruct2FunctionPointerFromJava() {

        //given
        final Pointer<StructFunc2> pointerStructFunc2 = nref(new StructFunc2() {
            @Override
            public long $(@ByVal(TestStruct.class) final long tst,
                          final byte field0,
                          @Unsigned final short field1,
                          @Ptr(int.class) final long field2,
                          @Ptr(int.class) final long field3,
                          @Lng final long embedded_field0,
                          final float embedded_field1) {
                return structTest2(tst,
                                   field0,
                                   field1,
                                   field2,
                                   field3,
                                   embedded_field0,
                                   embedded_field1);
            }
        });

        //when
        try (Pointer<TestStruct> tst = ref(new TestStruct());
             Pointer<Integer> intp = Pointer.nref(44)) {

            final byte  field0   = 'a';
            final short field1   = 22;
            final int   field2_0 = 123;
            final int   field2_1 = 456;
            final int   field2_2 = 789;

            final Pointer<Integer> field2 = Pointer.nref(field2_0,
                                                         field2_1,
                                                         field2_2);

            final Pointer<Integer> field3          = intp;
            final long             embedded_field0 = 1234567890L;
            final float            embedded_field1 = 9876543.21F;

            final Pointer<TestStruct> testStruct = wrap(TestStruct.class,
                                                        JNITestUtil.execStructTest2(pointerStructFunc2.address,
                                                                                    tst.address,
                                                                                    field0,
                                                                                    field1,
                                                                                    field2.address,
                                                                                    field3.address,
                                                                                    embedded_field0,
                                                                                    embedded_field1));

            //then
            final TestStruct testStruct1 = testStruct.dref();
            assertThat(testStruct1.field0()).isEqualTo(field0);
            assertThat(testStruct1.field1()).isEqualTo(field1);
            assertThat(testStruct1.field2()
                                  .dref(0)).isEqualTo(field2_0);
            assertThat(testStruct1.field2()
                                  .dref(1)).isEqualTo(field2_1);
            assertThat(testStruct1.field2()
                                  .dref(2)).isEqualTo(field2_2);
            assertThat(testStruct1.field3()).isEqualTo(field3);

            assertThat(testStruct1.field0()).isEqualTo(field0);
            assertThat(testStruct1.field1()).isEqualTo(field1);
            assertThat(testStruct1.field2()
                                  .dref(0)).isEqualTo(field2_0);
            assertThat(testStruct1.field2()
                                  .dref(1)).isEqualTo(field2_1);
            assertThat(testStruct1.field2()
                                  .dref(2)).isEqualTo(field2_2);
            assertThat(testStruct1.field3()).isEqualTo(field3);
            assertThat(testStruct1.field4()
                                  .field0()).isEqualTo(embedded_field0);
            assertThat(testStruct1.field4()
                                  .field1()).isEqualTo(embedded_field1);

            testStruct.close();
        }
    }

    public long structTest2(final long tstPointer,
                            final byte field0,
                            final short field1,
                            final long field2Array,
                            final long field3,
                            final long embedded_field0,
                            final float embedded_field1) {

        //@formatter:off
        final Pointer<TestStruct> someTest = malloc(TestStruct.SIZE, TestStruct.class);
        final TestStruct tst = wrap(TestStruct.class, tstPointer).dref();

        tst.field0(field0);
        tst.field1(field1);
        final Pointer<Integer> field2 = wrap(Integer.class, field2Array);
        tst.field2().writei(0, field2.dref(0));
        tst.field2().writei(1, field2.dref(1));
        tst.field2().writei(2, field2.dref(2));
        tst.field3(wrap(Integer.class, field3));
        tst.field4().field0(embedded_field0);
        tst.field4().field1(embedded_field1);

        someTest.dref().field0(tst.field0());
        someTest.dref().field1(tst.field1());
        someTest.dref().field2().writei(0, tst.field2().dref(0));
        someTest.dref().field2().writei(1, tst.field2().dref(1));
        someTest.dref().field2().writei(2, tst.field2().dref(2));
        someTest.dref().field3(tst.field3());
        someTest.dref().field4().field0(tst.field4().field0());
        someTest.dref().field4().field1(tst.field4().field1());

        return someTest.address;
        //@formatter:on
    }

    @Test
    public void testUnionFunctionPointerFromJava() {
        //given
        final Pointer<UnionFunc> pointerUnionFunc = nref(new UnionFunc() {
            @Override
            public long $(@Ptr(TestUnion.class) final long tst,
                          final int field0,
                          final float field1) {
                return unionTest(tst,
                                 field0,
                                 field1);
            }
        });

        final Pointer<TestUnion> testUnionPointer = malloc(TestUnion.SIZE).castp(TestUnion.class);
        final int                field0           = 123456789;
        final float              field1           = 9876.54F;

        //when
        try (Pointer<TestUnion> tst = testUnionPointer) {
            final Pointer<TestUnion> unionPointer = wrap(TestUnion.class,
                                                         JNITestUtil.execUnionTest(pointerUnionFunc.address,
                                                                                   tst.address,
                                                                                   field0,
                                                                                   field1));

            //then
            assertThat(tst.dref()
                          .field0()).isEqualTo(field0);
            assertThat(unionPointer.dref()
                                   .field1()).isEqualTo(field1);
        }
    }

    public long unionTest(final long tst,
                          final int field0,
                          final float field1) {
        wrap(TestUnion.class,
             tst).dref()
                 .field0(field0);

        final TestUnion someTest = new TestUnion();
        someTest.field1(field1);

        return ref(someTest).address;
    }

    @Test
    public void testUnion2FunctionPointerFromJava() {
        //given
        final Pointer<UnionFunc2> pointerUnionFunc = nref(new UnionFunc2() {
            @Override
            public long $(@Ptr(TestUnion.class) final long tst,
                          final int field0) {
                return unionTest2(tst,
                                  field0);
            }
        });


        final Pointer<TestUnion> testUnionPointer = malloc(TestUnion.SIZE).castp(TestUnion.class);
        final int                field0           = 123456789;

        //when
        try (Pointer<TestUnion> tst = testUnionPointer) {
            final Pointer<TestUnion> unionPointer = wrap(TestUnion.class,
                                                         JNITestUtil.execUnionTest2(pointerUnionFunc.address,
                                                                                    tst.address,
                                                                                    field0));

            //then
            assertThat(Float.floatToIntBits(unionPointer.dref()
                                                        .field1())).isEqualTo(field0);
            unionPointer.close();
        }
    }

    public long unionTest2(final long tstPointer,
                           final int field0) {
        final TestUnion tst = wrap(TestUnion.class,
                                   tstPointer).dref();
        tst.field0(field0);

        final Pointer<TestUnion> someTest = malloc(TestUnion.SIZE).castp(TestUnion.class);
        someTest.dref()
                .field1(tst.field1());
        return someTest.address;
    }

    @Test
    public void testCharFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = new Testing().charTestFunctionPointer();
        final Pointer<CharFunc> pointerCharFunc = wrap(CharFunc.class,
                                                       pointer);

        final byte value = 123;

        //when
        final byte retVal = pointerCharFunc.dref()
                                           .$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedCharFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = new Testing().unsignedCharTestFunctionPointer();
        final Pointer<UnsignedCharFunc> pointerUnsignedCharFunc = wrap(UnsignedCharFunc.class,
                                                                       pointer);

        final byte value = 123;

        //when
        final byte retVal = pointerUnsignedCharFunc.dref()
                                                   .$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testShortFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = new Testing().shortTestFunctionPointer();
        final Pointer<ShortFunc> pointerShortFunc = wrap(ShortFunc.class,
                                                         pointer);

        final short value = 32536;

        //when
        final short retVal = pointerShortFunc.dref()
                                             .$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedShortFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = new Testing().unsignedShortTestFunctionPointer();
        final Pointer<UnsignedShortFunc> pointerUnsignedShortFunc = wrap(UnsignedShortFunc.class,
                                                                         pointer);

        final short value = 32536;

        //when
        final short retVal = pointerUnsignedShortFunc.dref()
                                                     .$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testIntFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = new Testing().intTestFunctionPointer();
        final Pointer<IntFunc> pointerIntFunc = wrap(IntFunc.class,
                                                     pointer);

        final int value = 32536987;

        //when
        final int retVal = pointerIntFunc.dref()
                                         .$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedIntFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = new Testing().unsignedIntTestFunctionPointer();
        final Pointer<UnsignedIntFunc> pointerUnsignedIntFunc = wrap(UnsignedIntFunc.class,
                                                                     pointer);

        final int value = 32536987;

        //when
        final int retVal = pointerUnsignedIntFunc.dref()
                                                 .$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testLongFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = new Testing().longTestFunctionPointer();
        final Pointer<LongFunc> pointerLongFunc = wrap(LongFunc.class,
                                                       pointer);

        final int value = 32536456;

        //when
        final long retVal = pointerLongFunc.dref()
                                           .$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedLongFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = new Testing().unsignedLongTestFunctionPointer();
        final Pointer<UnsignedLongFunc> pointerUnsignedLongFunc = wrap(UnsignedLongFunc.class,
                                                                       pointer);

        final int value = 32536456;

        //when

        final long retVal = pointerUnsignedLongFunc.dref()
                                                   .$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testLongLongFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = new Testing().longLongTestFunctionPointer();
        final Pointer<LongLongFunc> pointerLongLongFunc = wrap(LongLongFunc.class,
                                                               pointer);

        final long value = 325364567789456L;

        //when
        final long retVal = pointerLongLongFunc.dref()
                                               .$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedLongLongFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = new Testing().unsignedLongLongTestFunctionPointer();
        final Pointer<UnsignedLongLongFunc> pointerUnsignedLongLongFunc = wrap(UnsignedLongLongFunc.class,
                                                                               pointer);

        final long value = 325364567789456L;

        //when
        final long retVal = pointerUnsignedLongLongFunc.dref()
                                                       .$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testFloatFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = new Testing().floatTestFunctionPointer();
        final Pointer<FloatFunc> pointerFloatFunc = wrap(FloatFunc.class,
                                                         pointer);

        final float value = 32536456.123456F;

        //when
        final float retVal = pointerFloatFunc.dref()
                                             .$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testDoubleFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = new Testing().doubleTestFunctionPointer();
        final Pointer<DoubleFunc> pointerDoubleFunc = wrap(DoubleFunc.class,
                                                           pointer);

        final double value = 32536456159753.12345615975D;

        //when
        final double retVal = pointerDoubleFunc.dref()
                                               .$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testPointerFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = new Testing().pointerTestFunctionPointer();
        final Pointer<PointerFunc> pointerPointerFunc = wrap(PointerFunc.class,
                                                             pointer);

        final int value = 32536456;

        //when
        final long retVal = pointerPointerFunc.dref()
                                              .$(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testStructFunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = Testing.structTestFunctionPointer();
        final Pointer<StructFunc> pointerStructFunc = wrap(StructFunc.class,
                                                           pointer);

        final Pointer<TestStruct> testStructPointer = malloc(TestStruct.SIZE).castp(TestStruct.class);
        final TestStruct          testStruct        = testStructPointer.dref();

        final byte             field0 = 10;
        final short            field1 = 20;
        final Pointer<Integer> field3 = Pointer.nref(40);

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
             Pointer<Integer> intp = Pointer.nref(44)) {

            final byte  newField0   = 'a';
            final short newField1   = 22;
            final int   newField2_0 = 123;
            final int   newField2_1 = 456;
            final int   newField2_2 = 789;

            final Pointer<Integer> newField2 = Pointer.nref(newField2_0,
                                                            newField2_1,
                                                            newField2_2);

            final Pointer<Integer> newField3       = intp;
            final long             embedded_field0 = 1234567890L;
            final float            embedded_field1 = 9876543.21F;

            final Pointer<TestStruct> testStructByValue = wrap(TestStruct.class,
                                                               pointerStructFunc.dref()
                                                                                .$(tst.address,
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
    public void testStruct2FunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = Testing.structTest2FunctionPointer();
        final Pointer<StructFunc2> pointerStructFunc2 = wrap(StructFunc2.class,
                                                             pointer);

        //when
        try (Pointer<TestStruct> tst = ref(new TestStruct());
             Pointer<Integer> intp = Pointer.nref(44)) {

            final byte  field0   = 'a';
            final short field1   = 22;
            final int   field2_0 = 123;
            final int   field2_1 = 456;
            final int   field2_2 = 789;

            final Pointer<Integer> field2 = Pointer.nref(field2_0,
                                                         field2_1,
                                                         field2_2);

            final Pointer<Integer> field3          = intp;
            final long             embedded_field0 = 1234567890L;
            final float            embedded_field1 = 9876543.21F;

            final Pointer<TestStruct> testStruct = wrap(TestStruct.class,
                                                        pointerStructFunc2.dref()
                                                                          .$(tst.address,
                                                                             field0,
                                                                             field1,
                                                                             field2.address,
                                                                             field3.address,
                                                                             embedded_field0,
                                                                             embedded_field1));

            //then
            final TestStruct testStruct1 = testStruct.dref();
            assertThat(testStruct1.field0()).isEqualTo(field0);
            assertThat(testStruct1.field1()).isEqualTo(field1);
            assertThat(testStruct1.field2()
                                  .dref(0)).isEqualTo(field2_0);
            assertThat(testStruct1.field2()
                                  .dref(1)).isEqualTo(field2_1);
            assertThat(testStruct1.field2()
                                  .dref(2)).isEqualTo(field2_2);
            assertThat(testStruct1.field3()).isEqualTo(field3);

            assertThat(testStruct1.field0()).isEqualTo(field0);
            assertThat(testStruct1.field1()).isEqualTo(field1);
            assertThat(testStruct1.field2()
                                  .dref(0)).isEqualTo(field2_0);
            assertThat(testStruct1.field2()
                                  .dref(1)).isEqualTo(field2_1);
            assertThat(testStruct1.field2()
                                  .dref(2)).isEqualTo(field2_2);
            assertThat(testStruct1.field3()).isEqualTo(field3);
            assertThat(testStruct1.field4()
                                  .field0()).isEqualTo(embedded_field0);
            assertThat(testStruct1.field4()
                                  .field1()).isEqualTo(embedded_field1);

            testStruct.close();
        }
    }

    @Test
    public void testUnionFunctionPointerFromC() {

        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = Testing.unionTestFunctionPointer();
        final Pointer<UnionFunc> pointerUnionFunc = wrap(UnionFunc.class,
                                                         pointer);

        final Pointer<TestUnion> testUnionPointer = malloc(TestUnion.SIZE).castp(TestUnion.class);
        final int                field0           = 123456789;
        final float              field1           = 9876.54F;

        //when
        try (Pointer<TestUnion> tst = testUnionPointer) {
            final Pointer<TestUnion> unionPointer = wrap(TestUnion.class,
                                                         pointerUnionFunc.dref()
                                                                         .$(tst.address,
                                                                            field0,
                                                                            field1));

            //then
            assertThat(tst.dref()
                          .field0()).isEqualTo(field0);
            assertThat(unionPointer.dref()
                                   .field1()).isEqualTo(field1);
        }
    }

    @Test
    public void testUnion2FunctionPointerFromC() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final long pointer = Testing.unionTest2FunctionPointer();
        final Pointer<UnionFunc2> pointerUnionFunc2 = wrap(UnionFunc2.class,
                                                           pointer);

        final Pointer<TestUnion> testUnionPointer = malloc(TestUnion.SIZE).castp(TestUnion.class);
        final int                field0           = 123456789;

        //when
        try (Pointer<TestUnion> tst = testUnionPointer) {
            final Pointer<TestUnion> unionPointer = wrap(TestUnion.class,
                                                         pointerUnionFunc2.dref()
                                                                          .$(tst.address,
                                                                             field0));
            //then
            assertThat(Float.floatToIntBits(unionPointer.dref()
                                                        .field1())).isEqualTo(field0);
            unionPointer.close();
        }
    }
}
