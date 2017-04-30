package org.freedesktop.jaccall;


import org.freedesktop.libtest.ReadGlobalVarFunc;
import org.freedesktop.libtest.TestStruct;
import org.freedesktop.libtest.TestUnion;
import org.freedesktop.libtest.Testing;
import org.freedesktop.libtest.Testing_Symbols;
import org.freedesktop.libtest.WriteGlobalVarFunc;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.truth.Truth.assertThat;
import static org.freedesktop.jaccall.Pointer.malloc;
import static org.freedesktop.jaccall.Pointer.nref;
import static org.freedesktop.jaccall.Pointer.wrap;
import static org.freedesktop.jaccall.Size.sizeof;

public class SymbolsTest {

    private static final String LIB_NAME = "testing";

    private static String libFilePath() {
        final InputStream libStream = SymbolsTest.class.getResourceAsStream("/libtesting.so");
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
    public void testChar() {
        //given
        final byte value = Byte.MAX_VALUE;

        //when
        final byte returned = new Testing().charTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testUnsignedChar() {
        //given
        final byte value = (byte) 0xFF;

        //when
        final byte returned = new Testing().unsignedCharTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testShort() {
        //given
        final short value = Short.MAX_VALUE;

        //when
        final short returned = new Testing().shortTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testUnsignedShort() {
        //given
        final short value = (short) 0xFFFF;

        //when
        final short returned = new Testing().unsignedShortTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testInt() {
        //given
        final int value = Integer.MAX_VALUE;

        //when
        final int returned = new Testing().intTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testUnsignedInt() {
        //given
        final int value = 0xFFFFFFFF;

        //when
        final int returned = new Testing().unsignedIntTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testLong() {
        //given
        final long value = sizeof((CLong) null) == 8 ? Long.MAX_VALUE : Integer.MAX_VALUE;

        //when
        final long returned = new Testing().longTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testUnsignedLong() {
        //given
        final long value = sizeof((CLong) null) == 8 ? 0xFFFFFFFFFFFFFFFFL : 0x00000000FFFFFFFFL;

        //when
        final long returned = new Testing().unsignedLongTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testLongLong() {
        //given
        final long value = Long.MAX_VALUE;

        //when
        final long returned = new Testing().longLongTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testUnsignedLongLong() {
        //given
        final long value = 0xFFFFFFFFFFFFFFFFL;

        //when
        final long returned = new Testing().unsignedLongLongTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testFloat() {
        //given
        final float value = Float.MAX_VALUE;

        //when
        final float returned = new Testing().floatTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testDouble() {
        //given
        final double value = Double.MAX_VALUE;

        //when
        final double returned = new Testing().doubleTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testPointer() {
        //given
        final long value = sizeof((Pointer) null) == 8 ? Long.MAX_VALUE : Integer.MAX_VALUE;

        //when
        final long returned = new Testing().pointerTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testStructReturnByReferencePassByValue() {
        //given
        final TestStruct          testStruct2 = new TestStruct();
        final Pointer<TestStruct> tst         = Pointer.ref(testStruct2);
        final Pointer<Integer>    intp        = nref(44);

        final byte  field0   = 'a';
        final short field1   = 22;
        final int   field2_0 = 123;
        final int   field2_1 = 456;
        final int   field2_2 = 789;

        final Pointer<Integer> field2 = nref(field2_0,
                                             field2_1,
                                             field2_2);

        final Pointer<Integer> field3          = intp;
        final long             embedded_field0 = 1234567890L;
        final float            embedded_field1 = 9876543.21F;

        //when
        final Pointer<TestStruct> testStruct = wrap(TestStruct.class,
                                                    Testing.structTest2(tst.address,
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
        assertThat(testStruct1.field2()
                              .get(0)).isEqualTo(field2_0);
        assertThat(testStruct1.field2()
                              .get(1)).isEqualTo(field2_1);
        assertThat(testStruct1.field2()
                              .get(2)).isEqualTo(field2_2);
        assertThat(testStruct1.field3()).isEqualTo(field3);

        assertThat(testStruct1.field0()).isEqualTo(field0);
        assertThat(testStruct1.field1()).isEqualTo(field1);
        assertThat(testStruct1.field2()
                              .get(0)).isEqualTo(field2_0);
        assertThat(testStruct1.field2()
                              .get(1)).isEqualTo(field2_1);
        assertThat(testStruct1.field2()
                              .get(2)).isEqualTo(field2_2);
        assertThat(testStruct1.field3()).isEqualTo(field3);
        assertThat(testStruct1.field4()
                              .field0()).isEqualTo(embedded_field0);
        assertThat(testStruct1.field4()
                              .field1()).isEqualTo(embedded_field1);

        testStruct.close();
    }

    @Test
    public void testStructReturnByValuePassByReference() {
        //given
        final Pointer<TestStruct> testStructPointer = malloc(TestStruct.SIZE).castp(TestStruct.class);
        final TestStruct          testStruct        = testStructPointer.get();

        final byte             field0 = 10;
        final short            field1 = 20;
        final Pointer<Integer> field3 = nref(40);

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
        final Pointer<TestStruct> tst  = testStructPointer;
        final Pointer<Integer>    intp = nref(44);

        final byte  newField0   = 'a';
        final short newField1   = 22;
        final int   newField2_0 = 123;
        final int   newField2_1 = 456;
        final int   newField2_2 = 789;

        final Pointer<Integer> newField2 = nref(newField2_0,
                                                newField2_1,
                                                newField2_2);

        final Pointer<Integer> newField3       = intp;
        final long             embedded_field0 = 1234567890L;
        final float            embedded_field1 = 9876543.21F;

        final Pointer<TestStruct> testStructByValue = wrap(TestStruct.class,
                                                           Testing.structTest(tst.address,
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
        assertThat(testStruct1.field2()
                              .get(0)).isEqualTo(newField2_0);
        assertThat(testStruct1.field2()
                              .get(1)).isEqualTo(newField2_1);
        assertThat(testStruct1.field2()
                              .get(2)).isEqualTo(newField2_2);
        assertThat(testStruct1.field3()).isEqualTo(newField3);

        assertThat(testStruct.field0()).isEqualTo(newField0);
        assertThat(testStruct.field1()).isEqualTo(newField1);
        assertThat(testStruct.field2()
                             .get(0)).isEqualTo(newField2_0);
        assertThat(testStruct.field2()
                             .get(1)).isEqualTo(newField2_1);
        assertThat(testStruct.field2()
                             .get(2)).isEqualTo(newField2_2);
        assertThat(testStruct.field3()).isEqualTo(newField3);

        testStructByValue.close();
    }

    @Test
    public void testUnionReturnByValuePassByReference() {
        //given
        final Pointer<TestUnion> testUnionPointer = malloc(TestUnion.SIZE).castp(TestUnion.class);
        final int                field0           = 123456789;
        final float              field1           = 9876.54F;

        //when
        try (Pointer<TestUnion> tst = testUnionPointer) {
            final Pointer<TestUnion> unionPointer = wrap(TestUnion.class,
                                                         Testing.unionTest(tst.address,
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
    public void testUnionReturnByReferencePassByValue() {
        //given
        final Pointer<TestUnion> testUnionPointer = malloc(TestUnion.SIZE).castp(TestUnion.class);
        final int                field0           = 123456789;

        //when
        try (Pointer<TestUnion> tst = testUnionPointer) {
            final Pointer<TestUnion> unionPointer = wrap(TestUnion.class,
                                                         Testing.unionTest2(tst.address,
                                                                            field0));

            assertThat(Float.floatToIntBits(unionPointer.get()
                                                        .field1())).isEqualTo(field0);
            unionPointer.close();
        }
    }

    @Test
    public void testNoArgs() {
        //given

        //when
        new Testing().noArgsTest();

        //then
        //no segfaults happen :)
    }

    @Test
    public void testNoArgsFuncPtr() {
        //given

        //when
        final long funcPtr = new Testing().noArgsFuncPtrTest();

        //then
        assertThat(funcPtr).isNotEqualTo(0);
    }

    @Test
    public void testSymbols() {
        //given

        final long readGlobalVarSymbol = Testing.readGlobalVar();
        final Pointer<ReadGlobalVarFunc> readGlobalVarFuncPointer = wrap(ReadGlobalVarFunc.class,
                                                                         readGlobalVarSymbol);

        final long globalvarSymbol = Testing.globalvar();
        final Pointer<Integer> globalVar = wrap(Integer.class,
                                                globalvarSymbol);

        final long writeGlobalVarSymbol = Testing.writeGlobalVar();
        final Pointer<WriteGlobalVarFunc> writeGlobalVarFuncPointer = wrap(WriteGlobalVarFunc.class,
                                                                           writeGlobalVarSymbol);

        //when
        writeGlobalVarFuncPointer.get()
                                 .invoke(10);

        //then
        assertThat(readGlobalVarFuncPointer.get()
                                           .invoke()).isEqualTo(10);
        assertThat(globalVar.get()).isEqualTo(10);
    }
}
