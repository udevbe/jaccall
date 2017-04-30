package org.freedesktop.jaccall;


import com.google.common.truth.Truth;
import org.freedesktop.libtest.CharFunc;
import org.freedesktop.libtest.DoubleFunc;
import org.freedesktop.libtest.FloatFunc;
import org.freedesktop.libtest.FooFunc;
import org.freedesktop.libtest.IntFunc;
import org.freedesktop.libtest.LongFunc;
import org.freedesktop.libtest.LongLongFunc;
import org.freedesktop.libtest.PointerDoubleFunc;
import org.freedesktop.libtest.PointerFloatFunc;
import org.freedesktop.libtest.PointerFooFunc;
import org.freedesktop.libtest.PointerFunc;
import org.freedesktop.libtest.ShortFunc;
import org.freedesktop.libtest.StructFunc;
import org.freedesktop.libtest.StructFunc2;
import org.freedesktop.libtest.TestStruct;
import org.freedesktop.libtest.TestUnion;
import org.freedesktop.libtest.Testing;
import org.freedesktop.libtest.Testing_Symbols;
import org.freedesktop.libtest.UnionFunc;
import org.freedesktop.libtest.UnionFunc2;
import org.freedesktop.libtest.UnsignedCharFunc;
import org.freedesktop.libtest.UnsignedIntFunc;
import org.freedesktop.libtest.UnsignedLongFunc;
import org.freedesktop.libtest.UnsignedLongLongFunc;
import org.freedesktop.libtest.UnsignedShortFunc;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.truth.Truth.assertThat;
import static org.freedesktop.libtest.PointerCharFunc.nref;
import static org.freedesktop.libtest.PointerDoubleFunc.nref;
import static org.freedesktop.libtest.PointerFloatFunc.nref;
import static org.freedesktop.libtest.PointerIntFunc.nref;
import static org.freedesktop.libtest.PointerLongFunc.nref;
import static org.freedesktop.libtest.PointerLongLongFunc.nref;
import static org.freedesktop.libtest.PointerPointerFunc.nref;
import static org.freedesktop.libtest.PointerShortFunc.nref;
import static org.freedesktop.libtest.PointerStructFunc.nref;
import static org.freedesktop.libtest.PointerStructFunc2.nref;
import static org.freedesktop.libtest.PointerUnionFunc.nref;
import static org.freedesktop.libtest.PointerUnionFunc2.nref;
import static org.freedesktop.libtest.PointerUnsignedCharFunc.nref;
import static org.freedesktop.libtest.PointerUnsignedIntFunc.nref;
import static org.freedesktop.libtest.PointerUnsignedLongFunc.nref;
import static org.freedesktop.libtest.PointerUnsignedLongLongFunc.nref;
import static org.freedesktop.libtest.PointerUnsignedShortFunc.nref;

public class FunctorTest {

    private static final String LIB_NAME = "testing";

    private static String libFilePath() {
        final InputStream libStream = FunctorTest.class.getResourceAsStream("/libtesting.so");
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

    @BeforeClass
    public static void beforeClass() {
        new Testing_Symbols().link(libFilePath());

    }

    @Test
    public void testCallFromC() {
        //given
        final long funcPtrAddr = new Testing().getFunctionPointerTest();
        final Pointer<FooFunc> pointerTestFunc = Pointer.wrap(FooFunc.class,
                                                              funcPtrAddr);

        try (final Pointer<TestStruct> arg0 = Pointer.malloc(TestStruct.SIZE,
                                                             TestStruct.class);
             final Pointer<TestStruct> arg2 = Pointer.malloc(TestStruct.SIZE,
                                                             TestStruct.class);
             final Pointer<Integer> field3 = Pointer.malloc(Size.sizeof((Integer) null))
                                                    .castp(Integer.class)) {

            arg2.get()
                .field0((byte) 123);
            arg2.get()
                .field1((short) 345);
            arg2.get()
                .field3(field3);

            //when
            final byte result = pointerTestFunc.get()
                                               .invoke(arg0.address,
                                                       567,
                                                       arg2.address);

            //then
            assertThat(result).isEqualTo((byte) 123);
            Truth.assertThat(arg0.get()
                                 .field2()
                                 .get(0))
                 .isEqualTo(567);
            Truth.assertThat(arg0.get()
                                 .field2()
                                 .get(1))
                 .isEqualTo(345);
            Truth.assertThat(arg0.get()
                                 .field3().address)
                 .isEqualTo(field3.address);
        }
    }

    @Test
    public void testCallFromJava() {
        //given
        final PointerFooFunc pointerTestFunc = PointerFooFunc.nref(
                new FooFunc() {
                    @Override
                    public byte invoke(@Ptr final long arg0,
                                       @Unsigned final int arg1,
                                       @ByVal(TestStruct.class) final long arg2) {
                        return function(arg0,
                                        arg1,
                                        arg2);
                    }
                });

        try (final Pointer<TestStruct> arg0 = Pointer.malloc(TestStruct.SIZE,
                                                             TestStruct.class);
             final Pointer<TestStruct> arg2 = Pointer.malloc(TestStruct.SIZE,
                                                             TestStruct.class);
             final Pointer<Integer> field3 = Pointer.malloc(Size.sizeof((Integer) null),
                                                            Integer.class)) {

            arg2.get()
                .field0((byte) 123);
            arg2.get()
                .field1((short) 345);
            arg2.get()
                .field3(field3);

            //when
            final byte result = new Testing().functionPointerTest(pointerTestFunc.address,
                                                                  arg0.address,
                                                                  567,
                                                                  arg2.address);

            //then
            assertThat(result).isEqualTo((byte) 123);
            Truth.assertThat(arg0.get()
                                 .field2()
                                 .get(0))
                 .isEqualTo(567);
            Truth.assertThat(arg0.get()
                                 .field2()
                                 .get(1))
                 .isEqualTo(345);
            Truth.assertThat(arg0.get()
                                 .field3().address)
                 .isEqualTo(field3.address);
        }
    }

    public byte function(@Ptr(StructType.class) final long arg0,
                         @Unsigned final int arg1,
                         @ByVal(TestStruct.class) final long arg2) {

        final Pointer<TestStruct> testStructByRef = Pointer.wrap(TestStruct.class,
                                                                 arg0);
        final TestStruct testStructByVal = Pointer.wrap(TestStruct.class,
                                                        arg2)
                                                  .get();

        testStructByRef.get()
                       .field2()
                       .set(0,
                            arg1);
        testStructByRef.get()
                       .field2()
                       .set(1,
                            (int) testStructByVal.field1());

        testStructByRef.get()
                       .field3(testStructByVal.field3());

        return testStructByVal.field0();
    }

    @Test
    public void testCharFunctionPointerFromJava() {
        //given
        final Pointer<CharFunc> pointerCharTest = nref(new CharFunc() {
            @Override
            public byte invoke(final byte value) {
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
            public byte invoke(final byte value) {
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
            public short invoke(final short value) {
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
            public short invoke(final short value) {
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
        final Pointer<IntFunc> pointerIntTest = nref(new IntFunc() {
            @Override
            public int invoke(final int value) {
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
            public int invoke(final int value) {
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
        final Pointer<LongFunc> pointerLongTest = nref(new LongFunc() {
            @Override
            public long invoke(final long value) {
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
            public long invoke(final long value) {
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
            public long invoke(final long value) {
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
            public long invoke(final long value) {
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
            public float invoke(final float value) {
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
            public double invoke(final double value) {
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
            public long invoke(final long value) {
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
            public long invoke(final long tst,
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

        final Pointer<TestStruct> testStructPointer = Pointer.malloc(TestStruct.SIZE,
                                                                     TestStruct.class);
        final TestStruct testStruct = testStructPointer.get();

        final byte             field0 = 10;
        final short            field1 = 20;
        final Pointer<Integer> field3 = Pointer.nref(40);

        testStruct.field0(field0);
        testStruct.field1(field1);
        testStruct.field2()
                  .set(1,
                       1);
        testStruct.field2()
                  .set(2,
                       11);
        testStruct.field2()
                  .set(3,
                       111);
        testStruct.field3(field3);

        //when
        final Pointer<Integer> intp = Pointer.nref(44);
        try (Pointer<TestStruct> tst = testStructPointer) {

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

            final Pointer<TestStruct> testStructByValue = Pointer.wrap(TestStruct.class,
                                                                       JNITestUtil.execStructTest(pointerStructFunc.address,
                                                                                                  tst.address,
                                                                                                  newField0,
                                                                                                  newField1,
                                                                                                  newField2.address,
                                                                                                  newField3.address,
                                                                                                  embedded_field0,
                                                                                                  embedded_field1));
            //then
            final TestStruct testStruct1 = testStructByValue.get();
            assertThat(testStruct1.field0()).isEqualTo(newField0);
            assertThat(testStruct1.field1()).isEqualTo(newField1);
            Truth.assertThat(testStruct1.field2()
                                        .get(0))
                 .isEqualTo(newField2_0);
            Truth.assertThat(testStruct1.field2()
                                        .get(1))
                 .isEqualTo(newField2_1);
            Truth.assertThat(testStruct1.field2()
                                        .get(2))
                 .isEqualTo(newField2_2);
            Truth.assertThat(testStruct1.field3())
                 .isEqualTo(newField3);

            assertThat(testStruct.field0()).isEqualTo(newField0);
            assertThat(testStruct.field1()).isEqualTo(newField1);
            Truth.assertThat(testStruct.field2()
                                       .get(0))
                 .isEqualTo(newField2_0);
            Truth.assertThat(testStruct.field2()
                                       .get(1))
                 .isEqualTo(newField2_1);
            Truth.assertThat(testStruct.field2()
                                       .get(2))
                 .isEqualTo(newField2_2);
            Truth.assertThat(testStruct.field3())
                 .isEqualTo(newField3);

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

        tst.get().field0(field0);
        tst.get().field1(field1);
        final Pointer<Integer> field2 = Pointer.wrap(Integer.class, field2Array);
        tst.get().field2().set(0, field2.get(0));
        tst.get().field2().set(1, field2.get(1));
        tst.get().field2().set(2, field2.get(2));
        tst.get().field3(Pointer.wrap(Integer.class, field3));
        tst.get().field4().field0(embedded_field0);
        tst.get().field4().field1(embedded_field1);

        final TestStruct someTest = new TestStruct();
        someTest.field0(tst.get().field0());
        someTest.field1(tst.get().field1());
        someTest.field2().set(0,tst.get().field2().get(0));
        someTest.field2().set(1,tst.get().field2().get(1));
        someTest.field2().set(2,tst.get().field2().get(2));
        someTest.field3(tst.get().field3());
        someTest.field4().field0(tst.get().field4().field0());
        someTest.field4().field1(tst.get().field4().field1());

        return Pointer.ref(someTest).address;
        //@formatter:on
    }

    @Test
    public void testStruct2FunctionPointerFromJava() {
        //given
        final Pointer<StructFunc2> pointerStructFunc2 = nref(new StructFunc2() {
            @Ptr(TestStruct.class)
            @Override
            public long invoke(@ByVal(TestStruct.class) final long tst,
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
        final Pointer<Integer>    intp        = Pointer.nref(44);
        final TestStruct          testStruct2 = new TestStruct();
        final Pointer<TestStruct> tst         = Pointer.ref(testStruct2);

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

        final Pointer<TestStruct> testStruct = Pointer.wrap(TestStruct.class,
                                                            JNITestUtil.execStructTest2(pointerStructFunc2.address,
                                                                                        tst.address,
                                                                                        field0,
                                                                                        field1,
                                                                                        field2.address,
                                                                                        field3.address,
                                                                                        embedded_field0,
                                                                                        embedded_field1));

        //then
        final TestStruct testStruct1 = testStruct.get();
        assertThat(testStruct1.field0()).isEqualTo(field0);
        assertThat(testStruct1.field1()).isEqualTo(field1);
        Truth.assertThat(testStruct1.field2()
                                    .get(0))
             .isEqualTo(field2_0);
        Truth.assertThat(testStruct1.field2()
                                    .get(1))
             .isEqualTo(field2_1);
        Truth.assertThat(testStruct1.field2()
                                    .get(2))
             .isEqualTo(field2_2);
        Truth.assertThat(testStruct1.field3())
             .isEqualTo(field3);

        assertThat(testStruct1.field0()).isEqualTo(field0);
        assertThat(testStruct1.field1()).isEqualTo(field1);
        Truth.assertThat(testStruct1.field2()
                                    .get(0))
             .isEqualTo(field2_0);
        Truth.assertThat(testStruct1.field2()
                                    .get(1))
             .isEqualTo(field2_1);
        Truth.assertThat(testStruct1.field2()
                                    .get(2))
             .isEqualTo(field2_2);
        Truth.assertThat(testStruct1.field3())
             .isEqualTo(field3);
        assertThat(testStruct1.field4()
                              .field0()).isEqualTo(embedded_field0);
        assertThat(testStruct1.field4()
                              .field1()).isEqualTo(embedded_field1);

        testStruct.close();
    }

    public long structTest2(final long tstPointer,
                            final byte field0,
                            final short field1,
                            final long field2Array,
                            final long field3,
                            final long embedded_field0,
                            final float embedded_field1) {
        //@formatter:off
        final Pointer<TestStruct> someTest = Pointer.malloc(TestStruct.SIZE, TestStruct.class);
        final TestStruct tst = Pointer.wrap(TestStruct.class, tstPointer).get();

        tst.field0(field0);
        tst.field1(field1);
        final Pointer<Integer> field2 = Pointer.wrap(Integer.class, field2Array);
        tst.field2().set(0, field2.get(0));
        tst.field2().set(1, field2.get(1));
        tst.field2().set(2, field2.get(2));
        tst.field3(Pointer.wrap(Integer.class, field3));
        tst.field4().field0(embedded_field0);
        tst.field4().field1(embedded_field1);

        someTest.get().field0(tst.field0());
        someTest.get().field1(tst.field1());
        someTest.get().field2().set(0, tst.field2().get(0));
        someTest.get().field2().set(1, tst.field2().get(1));
        someTest.get().field2().set(2, tst.field2().get(2));
        someTest.get().field3(tst.field3());
        someTest.get().field4().field0(tst.field4().field0());
        someTest.get().field4().field1(tst.field4().field1());

        return someTest.address;
        //@formatter:on
    }

    @Test
    public void testUnionFunctionPointerFromJava() {
        //given
        final Pointer<UnionFunc> pointerUnionFunc = nref(new UnionFunc() {
            @ByVal(TestUnion.class)
            @Override
            public long invoke(@Ptr(TestUnion.class) final long tst,
                               final int field0,
                               final float field1) {
                return unionTest(tst,
                                 field0,
                                 field1);
            }
        });

        final Pointer<TestUnion> testUnionPointer = Pointer.malloc(TestUnion.SIZE,
                                                                   TestUnion.class);
        final int   field0 = 123456789;
        final float field1 = 9876.54F;

        //when
        try (Pointer<TestUnion> tst = testUnionPointer) {
            final Pointer<TestUnion> unionPointer = Pointer.wrap(TestUnion.class,
                                                                 JNITestUtil.execUnionTest(pointerUnionFunc.address,
                                                                                           tst.address,
                                                                                           field0,
                                                                                           field1));

            //then
            assertThat(tst.get()
                          .field0()).isEqualTo(field0);
            assertThat(unionPointer.get()
                                   .field1()).isEqualTo(field1);
        }
    }

    public long unionTest(final long tst,
                          final int field0,
                          final float field1) {
        Pointer.wrap(TestUnion.class,
                     tst)
               .get()
               .field0(field0);

        final TestUnion someTest = new TestUnion();
        someTest.field1(field1);

        return Pointer.ref(someTest).address;
    }

    @Test
    public void testUnion2FunctionPointerFromJava() {
        //given
        final Pointer<UnionFunc2> pointerUnionFunc = nref(new UnionFunc2() {
            @Ptr(TestUnion.class)
            @Override
            public long invoke(@ByVal(TestUnion.class) final long tst,
                               final int field0) {
                return unionTest2(tst,
                                  field0);
            }
        });


        final Pointer<TestUnion> testUnionPointer = Pointer.malloc(TestUnion.SIZE,
                                                                   TestUnion.class);
        final int field0 = 123456789;

        //when
        try (Pointer<TestUnion> tst = testUnionPointer) {
            final Pointer<TestUnion> unionPointer = Pointer.wrap(TestUnion.class,
                                                                 JNITestUtil.execUnionTest2(pointerUnionFunc.address,
                                                                                            tst.address,
                                                                                            field0));

            //then
            assertThat(Float.floatToIntBits(unionPointer.get()
                                                        .field1())).isEqualTo(field0);
            unionPointer.close();
        }
    }

    public long unionTest2(final long tstPointer,
                           final int field0) {
        final TestUnion tst = Pointer.wrap(TestUnion.class,
                                           tstPointer)
                                     .get();
        tst.field0(field0);

        final Pointer<TestUnion> someTest = Pointer.malloc(TestUnion.SIZE,
                                                           TestUnion.class);
        someTest.get()
                .field1(tst.field1());
        return someTest.address;
    }

    @Test
    public void testCharFunctionPointerFromC() {
        //given
        final long pointer = new Testing().charTestFunctionPointer();
        final Pointer<CharFunc> pointerCharFunc = Pointer.wrap(CharFunc.class,
                                                               pointer);

        final byte value = 123;

        //when
        final byte retVal = pointerCharFunc.get()
                                           .invoke(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedCharFunctionPointerFromC() {
        //given
        final long pointer = new Testing().unsignedCharTestFunctionPointer();
        final Pointer<UnsignedCharFunc> pointerUnsignedCharFunc = Pointer.wrap(UnsignedCharFunc.class,
                                                                               pointer);

        final byte value = 123;

        //when
        final byte retVal = pointerUnsignedCharFunc.get()
                                                   .invoke(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testShortFunctionPointerFromC() {
        //given
        final long pointer = new Testing().shortTestFunctionPointer();
        final Pointer<ShortFunc> pointerShortFunc = Pointer.wrap(ShortFunc.class,
                                                                 pointer);

        final short value = 32536;

        //when
        final short retVal = pointerShortFunc.get()
                                             .invoke(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedShortFunctionPointerFromC() {
        //given
        final long pointer = new Testing().unsignedShortTestFunctionPointer();
        final Pointer<UnsignedShortFunc> pointerUnsignedShortFunc = Pointer.wrap(UnsignedShortFunc.class,
                                                                                 pointer);

        final short value = 32536;

        //when
        final short retVal = pointerUnsignedShortFunc.get()
                                                     .invoke(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testIntFunctionPointerFromC() {
        //given
        final long pointer = new Testing().intTestFunctionPointer();
        final Pointer<IntFunc> pointerIntFunc = Pointer.wrap(IntFunc.class,
                                                             pointer);

        final int value = 32536987;

        //when
        final int retVal = pointerIntFunc.get()
                                         .invoke(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedIntFunctionPointerFromC() {
        //given
        final long pointer = new Testing().unsignedIntTestFunctionPointer();
        final Pointer<UnsignedIntFunc> pointerUnsignedIntFunc = Pointer.wrap(UnsignedIntFunc.class,
                                                                             pointer);

        final int value = 32536987;

        //when
        final int retVal = pointerUnsignedIntFunc.get()
                                                 .invoke(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testLongFunctionPointerFromC() {
        //given
        final long pointer = new Testing().longTestFunctionPointer();
        final Pointer<LongFunc> pointerLongFunc = Pointer.wrap(LongFunc.class,
                                                               pointer);

        final int value = 32536456;

        //when
        final long retVal = pointerLongFunc.get()
                                           .invoke(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedLongFunctionPointerFromC() {
        //given
        final long pointer = new Testing().unsignedLongTestFunctionPointer();
        final Pointer<UnsignedLongFunc> pointerUnsignedLongFunc = Pointer.wrap(UnsignedLongFunc.class,
                                                                               pointer);

        final int value = 32536456;

        //when

        final long retVal = pointerUnsignedLongFunc.get()
                                                   .invoke(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testLongLongFunctionPointerFromC() {
        //given
        final long pointer = new Testing().longLongTestFunctionPointer();
        final Pointer<LongLongFunc> pointerLongLongFunc = Pointer.wrap(LongLongFunc.class,
                                                                       pointer);

        final long value = 325364567789456L;

        //when
        final long retVal = pointerLongLongFunc.get()
                                               .invoke(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testUnsignedLongLongFunctionPointerFromC() {
        //given
        final long pointer = new Testing().unsignedLongLongTestFunctionPointer();
        final Pointer<UnsignedLongLongFunc> pointerUnsignedLongLongFunc = Pointer.wrap(UnsignedLongLongFunc.class,
                                                                                       pointer);

        final long value = 325364567789456L;

        //when
        final long retVal = pointerUnsignedLongLongFunc.get()
                                                       .invoke(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testFloatFunctionPointerFromC() {
        //given
        final long pointer = new Testing().floatTestFunctionPointer();
        final Pointer<FloatFunc> pointerFloatFunc = Pointer.wrap(FloatFunc.class,
                                                                 pointer);

        final float value = 32536456.123456F;

        //when
        final float retVal = pointerFloatFunc.get()
                                             .invoke(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testDoubleFunctionPointerFromC() {
        //given
        final long pointer = new Testing().doubleTestFunctionPointer();
        final Pointer<DoubleFunc> pointerDoubleFunc = Pointer.wrap(DoubleFunc.class,
                                                                   pointer);

        final double value = 32536456159753.12345615975D;

        //when
        final double retVal = pointerDoubleFunc.get()
                                               .invoke(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testPointerFunctionPointerFromC() {
        //given
        final long pointer = new Testing().pointerTestFunctionPointer();
        final Pointer<PointerFunc> pointerPointerFunc = Pointer.wrap(PointerFunc.class,
                                                                     pointer);

        final int value = 32536456;

        //when
        final long retVal = pointerPointerFunc.get()
                                              .invoke(value);

        //then
        assertThat(retVal).isEqualTo(value);
    }

    @Test
    public void testStructFunctionPointerFromC() {
        //given
        final long pointer = Testing.structTestFunctionPointer();
        final Pointer<StructFunc> pointerStructFunc = Pointer.wrap(StructFunc.class,
                                                                   pointer);

        final Pointer<TestStruct> testStructPointer = Pointer.malloc(TestStruct.SIZE,
                                                                     TestStruct.class);
        final TestStruct testStruct = testStructPointer.get();

        final byte             field0 = 10;
        final short            field1 = 20;
        final Pointer<Integer> field3 = Pointer.nref(40);

        testStruct.field0(field0);
        testStruct.field1(field1);
        testStruct.field2()
                  .set(1,
                       1);
        testStruct.field2()
                  .set(2,
                       11);
        testStruct.field2()
                  .set(3,
                       111);
        testStruct.field3(field3);

        //when
        final Pointer<Integer> intp = Pointer.nref(44);
        try (Pointer<TestStruct> tst = testStructPointer) {

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

            final Pointer<TestStruct> testStructByValue = Pointer.wrap(TestStruct.class,
                                                                       pointerStructFunc.get()
                                                                                        .invoke(tst.address,
                                                                                                newField0,
                                                                                                newField1,
                                                                                                newField2.address,
                                                                                                newField3.address,
                                                                                                embedded_field0,
                                                                                                embedded_field1));

            //then
            final TestStruct testStruct1 = testStructByValue.get();
            assertThat(testStruct1.field0()).isEqualTo(newField0);
            assertThat(testStruct1.field1()).isEqualTo(newField1);
            Truth.assertThat(testStruct1.field2()
                                        .get(0))
                 .isEqualTo(newField2_0);
            Truth.assertThat(testStruct1.field2()
                                        .get(1))
                 .isEqualTo(newField2_1);
            Truth.assertThat(testStruct1.field2()
                                        .get(2))
                 .isEqualTo(newField2_2);
            Truth.assertThat(testStruct1.field3())
                 .isEqualTo(newField3);

            assertThat(testStruct.field0()).isEqualTo(newField0);
            assertThat(testStruct.field1()).isEqualTo(newField1);
            Truth.assertThat(testStruct.field2()
                                       .get(0))
                 .isEqualTo(newField2_0);
            Truth.assertThat(testStruct.field2()
                                       .get(1))
                 .isEqualTo(newField2_1);
            Truth.assertThat(testStruct.field2()
                                       .get(2))
                 .isEqualTo(newField2_2);
            Truth.assertThat(testStruct.field3())
                 .isEqualTo(newField3);

            testStructByValue.close();
        }
    }

    @Test
    public void testStruct2FunctionPointerFromC() {
        //given
        final long pointer = Testing.structTest2FunctionPointer();
        final Pointer<StructFunc2> pointerStructFunc2 = Pointer.wrap(StructFunc2.class,
                                                                     pointer);

        //when
        final Pointer<Integer>    intp        = Pointer.nref(44);
        final TestStruct          testStruct2 = new TestStruct();
        final Pointer<TestStruct> tst         = Pointer.ref(testStruct2);

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

        final Pointer<TestStruct> testStruct = Pointer.wrap(TestStruct.class,
                                                            pointerStructFunc2.get()
                                                                              .invoke(tst.address,
                                                                                      field0,
                                                                                      field1,
                                                                                      field2.address,
                                                                                      field3.address,
                                                                                      embedded_field0,
                                                                                      embedded_field1));

        //then
        final TestStruct testStruct1 = testStruct.get();
        assertThat(testStruct1.field0()).isEqualTo(field0);
        assertThat(testStruct1.field1()).isEqualTo(field1);
        Truth.assertThat(testStruct1.field2()
                                    .get(0))
             .isEqualTo(field2_0);
        Truth.assertThat(testStruct1.field2()
                                    .get(1))
             .isEqualTo(field2_1);
        Truth.assertThat(testStruct1.field2()
                                    .get(2))
             .isEqualTo(field2_2);
        Truth.assertThat(testStruct1.field3())
             .isEqualTo(field3);

        assertThat(testStruct1.field0()).isEqualTo(field0);
        assertThat(testStruct1.field1()).isEqualTo(field1);
        Truth.assertThat(testStruct1.field2()
                                    .get(0))
             .isEqualTo(field2_0);
        Truth.assertThat(testStruct1.field2()
                                    .get(1))
             .isEqualTo(field2_1);
        Truth.assertThat(testStruct1.field2()
                                    .get(2))
             .isEqualTo(field2_2);
        Truth.assertThat(testStruct1.field3())
             .isEqualTo(field3);
        assertThat(testStruct1.field4()
                              .field0()).isEqualTo(embedded_field0);
        assertThat(testStruct1.field4()
                              .field1()).isEqualTo(embedded_field1);

        testStruct.close();
    }

    @Test
    public void testUnionFunctionPointerFromC() {
        //given
        final long pointer = Testing.unionTestFunctionPointer();
        final Pointer<UnionFunc> pointerUnionFunc = Pointer.wrap(UnionFunc.class,
                                                                 pointer);

        final Pointer<TestUnion> testUnionPointer = Pointer.malloc(TestUnion.SIZE)
                                                           .castp(TestUnion.class);
        final int   field0 = 123456789;
        final float field1 = 9876.54F;

        //when
        try (Pointer<TestUnion> tst = testUnionPointer) {
            final Pointer<TestUnion> unionPointer = Pointer.wrap(TestUnion.class,
                                                                 pointerUnionFunc.get()
                                                                                 .invoke(tst.address,
                                                                                         field0,
                                                                                         field1));

            //then
            assertThat(tst.get()
                          .field0()).isEqualTo(field0);
            assertThat(unionPointer.get()
                                   .field1()).isEqualTo(field1);
        }
    }

    @Test
    public void testUnion2FunctionPointerFromC() {
        //given
        final long pointer = Testing.unionTest2FunctionPointer();
        final Pointer<UnionFunc2> pointerUnionFunc2 = Pointer.wrap(UnionFunc2.class,
                                                                   pointer);

        final Pointer<TestUnion> testUnionPointer = Pointer.malloc(TestUnion.SIZE,
                                                                   TestUnion.class);
        final int field0 = 123456789;

        //when
        try (Pointer<TestUnion> tst = testUnionPointer) {
            final Pointer<TestUnion> unionPointer = Pointer.wrap(TestUnion.class,
                                                                 pointerUnionFunc2.get()
                                                                                  .invoke(tst.address,
                                                                                          field0));
            //then
            assertThat(Float.floatToIntBits(unionPointer.get()
                                                        .field1())).isEqualTo(field0);
            unionPointer.close();
        }
    }
}
