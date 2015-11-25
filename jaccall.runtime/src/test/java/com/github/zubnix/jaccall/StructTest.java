package com.github.zubnix.jaccall;


import com.github.zubnix.libtest.FieldsTestStruct;
import com.github.zubnix.libtest.TestStructEmbedded;
import com.github.zubnix.libtest.Testing;
import com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(JUnit4.class)
public class StructTest {

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
    public void testReadStructFieldTypes() {
        //given
        Linker.link(libFilePath(),
                    Testing.class,
                    new Testing_Jaccall_LinkSymbols());

        final FieldsTestStruct fieldsTestStruct = new FieldsTestStruct();
        final byte             charField        = 123;
        final short            shortField       = 12345;
        final int              intField         = 1234567890;
        final int              longField        = 987654321;
        final long             longLongField    = 1234567890123456789L;
        final float            floatField       = 1234.5678F;
        final double           doubleField      = 12345678.9012345;
        final Pointer<Void>    pointerField     = Pointer.malloc(10);
        final Pointer<Void>    pointer0         = Pointer.malloc(10);
        final Pointer<Void>    pointer1         = Pointer.malloc(20);
        final Pointer<Void>    pointer2         = Pointer.malloc(30);
        final Pointer<Pointer<Void>> pointerArrayField = Pointer.nref(pointer0,
                                                                      pointer1,
                                                                      pointer2);
        final TestStructEmbedded structField = new TestStructEmbedded();
        final TestStructEmbedded struct0     = new TestStructEmbedded();
        final TestStructEmbedded struct1     = new TestStructEmbedded();
        final TestStructEmbedded struct2     = new TestStructEmbedded();
        final Pointer<TestStructEmbedded> structArrayField = Pointer.nref(struct0,
                                                                          struct1,
                                                                          struct2);


        final Testing testing = new Testing();


//        //when
        testing.writeFieldsTestStruct(Pointer.ref(fieldsTestStruct).address,
                                      charField,
                                      shortField,
                                      intField,
                                      longField,
                                      longLongField,
                                      floatField,
                                      doubleField,
                                      pointerField.address,
                                      pointerArrayField.address,
                                      Pointer.ref(structField).address,
                                      structArrayField.address,
                                      3);

        //then
    }

    @Test
    public void testWriteStructFieldTypes() {

    }
}
