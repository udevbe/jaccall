package com.github.zubnix.jaccall;


import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

final class JNI {

    private static final String LIB_PREFIX  = "lib";
    private static final String LIB_NAME    = "jaccall";
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
        catch (IOException e) {
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

    private JNI() {
    }

    /*
     * JNI ->
     */
    static native ByteBuffer wrap(long address,
                                  @Nonnegative long size);

    static native long unwrap(@Nonnull ByteBuffer byteBuffer);
    /*
     * <- JNI
     */

    /*
     * std ->
     */
    static native long malloc(@Nonnegative int size);

    static native long calloc(@Nonnegative int nmemb,
                              @Nonnegative int size);

    static native void free(long address);

    static native int sizeOfPointer();

    static native int sizeOfCLong();

    /*
     * <- std
     */
    /*
     * linker ->
     */
    static native void link(Class<?> header,/*class with native methods*/
                            int nroSymbols,/* number of symbols*/
                            String[] symbols,/*method names*/
                            String[] jniSignatures,/*jni method signatures*/
                            String[] jaccallSignatures,/*simplified c method signature*/
                            long handle,/*handle to native lib*/
                            long[] symbolAddress);/*address of symbol (function pointer)*/
    /*
     * <- linker
     */

    /*
     * platform specific ->
     */
    static native long open(String filename);

    static native long sym(long handle,
                           String symbol);

    static native int close(long handle);
    /*
     * <- platform specific
     */

}
