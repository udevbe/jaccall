package com.github.zubnix.jaccall;


import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class JNI {

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
        catch (final IOException e) {
            throw new Error(e);
        }
    }

    private static void unpack(final InputStream libStream,
                               final File tempFile) throws IOException {
        final FileOutputStream fos    = new FileOutputStream(tempFile);
        final byte[]           buffer = new byte[4096];
        int                    read;
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
     * FFI ->
     */
    public static native long ffi_type_void();

    public static native long ffi_type_sint8();

    public static native long ffi_type_uint8();

    public static native long ffi_type_sint16();

    public static native long ffi_type_uint16();

    public static native long ffi_type_sint32();

    public static native long ffi_type_uint32();

    public static native long ffi_type_slong();

    public static native long ffi_type_ulong();

    public static native long ffi_type_sint64();

    public static native long ffi_type_uint64();

    public static native long ffi_type_float();

    public static native long ffi_type_double();

    public static native long ffi_type_pointer();

    public static native long ffi_type_struct(long... ffiTypes);

    public static native long ffi_type_union(long... ffiTypes);

    public static native int ffi_type_struct_size(long ffiStructType);

    public static native long ffi_callInterface(long return_type,
                                                long... arg_types);
    /*
     * <- ffi
     */

    /*
     * linker ->
     */

    static native void link(String library, /* library path */
                            Class<?> header,/*class with native methods*/
                            String[] symbols,/*method names*/
                            byte[] argumentSizes,/*number of arguments for each method*/
                            String[] jniSignatures,/*jni method signatures*/
                            long[] ffiCallInterfaces/*array of ffi type pointers*/
                           );
    /*
     * <- linker
     */
}
