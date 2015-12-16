package com.github.zubnix.jaccall;


import com.github.zubnix.libtest.TestStruct;
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
        catch (IOException e) {
            throw new Error(e);
        }
    }

    private static void unpack(final InputStream libStream,
                               final File tempFile) throws IOException {
        final FileOutputStream fos    = new FileOutputStream(tempFile);
        byte[]                 buffer = new byte[4096];
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
    public void testStaticMethodCall() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final Pointer<TestStruct> testStructPointer = malloc(TestStruct.SIZE).castp(TestStruct.class);
        final TestStruct          testStruct        = testStructPointer.dref();

        byte  field0 = 10;
        short field1 = 20;
        Pointer<Integer> field3 = nref(40);

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

            byte newField0 = 'a';
            short newField1 = 22;
            int newField2_0 = 123;
            int newField2_1 = 456;
            int newField2_2 = 789;

            Pointer<Integer> newField2 = nref(newField2_0,
                                              newField2_1,
                                              newField2_2);

            Pointer<Integer> newField3 = intp;

            final Pointer<TestStruct> testStructByValue = wrap(TestStruct.class,
                                                               Testing.doStaticTest(tst.address,
                                                                                    newField0,
                                                                                    newField1,
                                                                                    newField2.address,
                                                                                    newField3.address));
            System.err.println(TestStruct.SIZE);

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
}
