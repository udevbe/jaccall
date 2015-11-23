package com.github.zubnix.jaccall;


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
import static com.github.zubnix.jaccall.Size.sizeof;
import static com.google.common.truth.Truth.assertThat;

public class LinkerTest {

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
    public void testChar() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        //when
        byte       value    = Byte.MAX_VALUE;
        final byte returned = new Testing().charTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testUnsignedChar() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        //when
        byte       value    = (byte) 0xFF;
        final byte returned = new Testing().unsignedCharTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testShort() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        //when
        short       value    = Short.MAX_VALUE;
        final short returned = new Testing().shortTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testUnsignedShort() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        //when
        short       value    = (short) 0xFFFF;
        final short returned = new Testing().unsignedShortTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testInt() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        //when
        int       value    = Integer.MAX_VALUE;
        final int returned = new Testing().intTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testUnsignedInt() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        //when
        int       value    = 0xFFFFFFFF;
        final int returned = new Testing().unsignedIntTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testLong() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        //when
        long       value    = sizeof((CLong) null) == 8 ? Long.MAX_VALUE : Integer.MAX_VALUE;
        final long returned = new Testing().longTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testUnsignedLong() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        //when
        long       value    = sizeof((CLong) null) == 8 ? 0xFFFFFFFFFFFFFFFFL : 0xFFFFFFFF;
        final long returned = new Testing().unsignedLongTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testLongLong() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        //when
        long       value    = Long.MAX_VALUE;
        final long returned = new Testing().longLongTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testUnsignedLongLong() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        //when
        long       value    = 0xFFFFFFFFFFFFFFFFL;
        final long returned = new Testing().unsignedLongLongTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testFloat() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());
        //when
        float       value    = Float.MAX_VALUE;
        final float returned = new Testing().floatTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testDouble() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());
        //when
        double       value    = Double.MAX_VALUE;
        final double returned = new Testing().doubleTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testPointer() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());
        //when
        long       value    = Long.MAX_VALUE;
        final long returned = new Testing().pointerTest(value);

        //then
        assertThat(value).isEqualTo(returned);
    }

    @Test
    public void testStructReturnByReferencePassByValue() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        //when
        try (Pointer<TestStruct> tst = nref(new TestStruct());
             Pointer<Integer> intp = nref(44)) {

            final byte field0 = 'a';
            final short field1 = 22;
            final int field2_0 = 123;
            final int field2_1 = 456;
            final int field2_2 = 789;

            final Pointer<Integer> field2 = nref(field2_0,
                                                 field2_1,
                                                 field2_2);

            final Pointer<Integer> field3 = intp;
            final long embedded_field0 = 1234567890L;
            final float embedded_field1 = 9876543.21F;

            final Pointer<TestStruct> testStruct = wrap(TestStruct.class,
                                                        Testing.structTest2(tst.address,
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
    public void testStructReturnByValuePassByReference() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

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
                                                               Testing.structTest(tst.address,
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
    public void testUnionReturnByValuePassByReference() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        Pointer<TestUnion> testUnionPointer = malloc(TestUnion.SIZE).castp(TestUnion.class);
        int                field0           = 123456789;
        float              field1           = 9876.54F;

        //when
        try (Pointer<TestUnion> tst = testUnionPointer) {
            final Pointer<TestUnion> unionPointer = wrap(TestUnion.class,
                                                         Testing.unionTest(tst.address,
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
    public void testUnionReturnByReferencePassByValue() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        Pointer<TestUnion> testUnionPointer = malloc(TestUnion.SIZE).castp(TestUnion.class);
        int                field0           = 123456789;

        //when
        try (Pointer<TestUnion> tst = testUnionPointer) {
            final Pointer<TestUnion> unionPointer = wrap(TestUnion.class,
                                                         Testing.unionTest2(tst.address,
                                                                            field0));

            assertThat(Float.floatToIntBits(unionPointer.dref()
                                                        .field1())).isEqualTo(field0);
            unionPointer.close();
        }
    }

    @Test
    public void testNoArgs() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        //when
        new Testing().noArgsTest();

        //then
        //no segfaults happen :)
    }

    @Test
    public void testNoArgsFuncPtr() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        //when
        final long funcPtr = new Testing().noArgsFuncPtrTest();

        //then
        assertThat(funcPtr).isNotEqualTo(0);
    }
}
