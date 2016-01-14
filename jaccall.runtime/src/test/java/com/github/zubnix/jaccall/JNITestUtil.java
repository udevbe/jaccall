package com.github.zubnix.jaccall;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class JNITestUtil {

    private static final String LIB_PREFIX  = "lib";
    private static final String LIB_NAME    = "jnitestutil";
    private static final String LIB_POSTFIX = ".so";

    static {
        final InputStream libStream = JNI.class.getClassLoader()
                                               .getResourceAsStream(LIB_PREFIX + LIB_NAME + LIB_POSTFIX);
        try {
            final File tempFile = File.createTempFile(LIB_NAME,
                                                      null);
            tempFile.deleteOnExit();
            unpack(libStream,
                   tempFile);
            System.load(tempFile.getAbsolutePath());
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

    public static native long byteArrayAsPointer(int b0,
                                                 int b1,
                                                 int b2,
                                                 int b3,
                                                 int b4);

    public static native long pointerOfPointer(long pointer);

    public static native long readCLong(final long address);

    public static native byte readByte(final long address);

    public static native float readFloat(final long address);

    public static native float readDouble(final long address);

    public static native long readPointer(final long address);

    public static native byte execCharTest(long functionPointer,
                                           byte value);

    public static native byte execUnsignedCharTest(long functionPointer,
                                                   byte value);

    public static native short execShortTest(long functionPointer,
                                             short value);

    public static native short execUnsignedShortTest(long functionPointer,
                                                     short value);

    public static native int execIntTest(long functionPointer,
                                         int value);

    public static native int execUnsignedIntTest(long functionPointer,
                                                 int value);

    public static native long execLongTest(long functionPointer,
                                           long value);


    public static native long execUnsignedLongTest(long functionPointer,
                                                   long value);

    public static native long execLongLongTest(long functionPointer,
                                               long value);


    public static native long execUnsignedLongLongTest(long functionPointer,
                                                       long value);

    public static native float execFloatTest(long functionPointer,
                                             float value);

    public static native double execDoubleTest(long functionPointer,
                                               double value);

    public static native long execPointerTest(long functionPointer,
                                              long value);

    public static native long execStructTest(long functionPointer,
                                             long tst,
                                             byte field0,
                                             short field1,
                                             long field2,
                                             long field3,
                                             long embedded_field0,
                                             float embedded_field1);

    public static native long execStructTest2(long functionPointer,
                                              long tst,
                                              byte field0,
                                              short field1,
                                              long field2,
                                              long field3,
                                              long embedded_field0,
                                              float embedded_field1);

    public static native long execUnionTest(long functionPointer,
                                            long tst,
                                            int field0,
                                            float field1);

    public static native long execUnionTest2(long functionPointer,
                                             long tst,
                                             int field0);
}
