package org.freedesktop.jaccall;


import org.freedesktop.libtest.FieldsTestStruct;
import org.freedesktop.libtest.TestStructEmbedded;
import org.freedesktop.libtest.Testing;
import org.freedesktop.libtest.Testing_Symbols;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.truth.Truth.assertThat;

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

    @BeforeClass
    public static void beforeClass() {
        new Testing_Symbols().link(libFilePath());
    }

    @Test
    public void testReadStructFieldTypes() {
        //given
        final FieldsTestStruct fieldsTestStruct = new FieldsTestStruct();
        final byte             charField        = 123;
        final short            shortField       = 12345;
        final int              intField         = 1234567890;
        final CLong            longField        = new CLong(987654321);
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
        final TestStructEmbedded structField       = new TestStructEmbedded();
        final long               structFieldField0 = 9876543210L;
        final float              structFieldField1 = 9876.5432F;
        structField.field0(structFieldField0);
        structField.field1(structFieldField1);

        final TestStructEmbedded struct0  = new TestStructEmbedded();
        final long               struct00 = 876543210L;
        final float              struct01 = 876.5432F;
        struct0.field0(struct00);
        struct0.field1(struct01);

        final TestStructEmbedded struct1  = new TestStructEmbedded();
        final long               struct10 = 76543210L;
        final float              struct11 = 76.5432F;
        struct1.field0(struct10);
        struct1.field1(struct11);

        final TestStructEmbedded struct2  = new TestStructEmbedded();
        final long               struct20 = 6543210L;
        final float              struct21 = 6.5432F;
        struct2.field0(struct20);
        struct2.field1(struct21);

        final Pointer<TestStructEmbedded> structArrayField = Pointer.nref(struct0,
                                                                          struct1,
                                                                          struct2);

        final Testing testing = new Testing();


        //when
        testing.writeFieldsTestStruct(Pointer.ref(fieldsTestStruct).address,
                                      charField,
                                      shortField,
                                      intField,
                                      longField.longValue(),
                                      longLongField,
                                      floatField,
                                      doubleField,
                                      pointerField.address,
                                      pointerArrayField.address,
                                      Pointer.ref(structField).address,
                                      structArrayField.address,
                                      3);

        //then
        assertThat(fieldsTestStruct.charField()).isEqualTo(charField);
        assertThat(fieldsTestStruct.shortField()).isEqualTo(shortField);
        assertThat(fieldsTestStruct.charField()).isEqualTo(charField);
        assertThat(fieldsTestStruct.intField()).isEqualTo(intField);
        assertThat(fieldsTestStruct.longField()).isEqualTo(longField);
        assertThat(fieldsTestStruct.longLongField()).isEqualTo(longLongField);
        assertThat(fieldsTestStruct.floatField()).isEqualTo(floatField);
        assertThat(fieldsTestStruct.doubleField()).isEqualTo(doubleField);
        assertThat(fieldsTestStruct.pointerField()).isEqualTo(pointerField);
        assertThat(fieldsTestStruct.pointerArrayField()
                                   .dref(0)).isEqualTo(pointerArrayField.dref(0));
        assertThat(fieldsTestStruct.pointerArrayField()
                                   .dref(1)).isEqualTo(pointerArrayField.dref(1));
        assertThat(fieldsTestStruct.pointerArrayField()
                                   .dref(2)).isEqualTo(pointerArrayField.dref(2));
        assertThat(fieldsTestStruct.structField()
                                   .field0()).isEqualTo(structFieldField0);
        assertThat(fieldsTestStruct.structField()
                                   .field1()).isEqualTo(structFieldField1);
        assertThat(fieldsTestStruct.structArrayField()
                                   .dref(0)
                                   .field0()).isEqualTo(struct00);
        assertThat(fieldsTestStruct.structArrayField()
                                   .dref(0)
                                   .field1()).isEqualTo(struct01);
        assertThat(fieldsTestStruct.structArrayField()
                                   .dref(1)
                                   .field0()).isEqualTo(struct10);
        assertThat(fieldsTestStruct.structArrayField()
                                   .dref(1)
                                   .field1()).isEqualTo(struct11);
        assertThat(fieldsTestStruct.structArrayField()
                                   .dref(2)
                                   .field0()).isEqualTo(struct20);
        assertThat(fieldsTestStruct.structArrayField()
                                   .dref(2)
                                   .field1()).isEqualTo(struct21);
    }

    @Test
    public void testWriteStructFieldTypes() {

    }
}
