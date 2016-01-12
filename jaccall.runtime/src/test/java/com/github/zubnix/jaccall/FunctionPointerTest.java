package com.github.zubnix.jaccall;


import com.github.zubnix.libtest.PointerTestFunc;
import com.github.zubnix.libtest.TestFunc;
import com.github.zubnix.libtest.TestStruct;
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

        final long funcPtrAddr = new Testing().getFunctionPointerTest();
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
            assertThat(result).isEqualTo(123);
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
            assertThat(result).isEqualTo(123);
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
}
