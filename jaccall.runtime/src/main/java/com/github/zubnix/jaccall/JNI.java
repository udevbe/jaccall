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
    public static native ByteBuffer wrap(long address,
                                         @Nonnegative long size);

    public static native long unwrap(@Nonnull ByteBuffer byteBuffer);
    /*
     * <- JNI
     */

    /*
     * std ->
     */
    public static native long malloc(@Nonnegative int size);

    public static native long calloc(@Nonnegative int nmemb,
                                     @Nonnegative int size);

    public static native void free(long address);

    public static native int sizeOfPointer();

    public static native int sizeOfCLong();

    /*
     * <- std
     */

    /*
     * FFI ->
     */
    public static final long FFI_TYPE_VOID = ffi_type_void();

    private static native long ffi_type_void();

    public static final long FFI_TYPE_SINT8 = ffi_type_sint8();

    private static native long ffi_type_sint8();

    public static final long FFI_TYPE_UINT8 = ffi_type_uint8();

    private static native long ffi_type_uint8();

    public static final long FFI_TYPE_SINT16 = ffi_type_sint16();

    private static native long ffi_type_sint16();

    public static final long FFI_TYPE_UINT16 = ffi_type_uint16();

    private static native long ffi_type_uint16();

    public static final long FFI_TYPE_SINT32 = ffi_type_sint32();

    private static native long ffi_type_sint32();

    public static final long FFI_TYPE_UINT32 = ffi_type_uint32();

    private static native long ffi_type_uint32();

    public static final long FFI_TYPE_SLONG = ffi_type_slong();

    private static native long ffi_type_slong();

    public static final long FFI_TYPE_ULONG = ffi_type_ulong();

    private static native long ffi_type_ulong();

    public static final long FFI_TYPE_SINT64 = ffi_type_sint64();

    private static native long ffi_type_sint64();

    public static final long FFI_TYPE_UINT64 = ffi_type_uint64();

    private static native long ffi_type_uint64();

    public static final long FFI_TYPE_FLOAT = ffi_type_float();

    private static native long ffi_type_float();

    public static final long FFI_TYPE_DOUBLE = ffi_type_double();

    private static native long ffi_type_double();

    public static final long FFI_TYPE_POINTER = ffi_type_pointer();

    private static native long ffi_type_pointer();

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

    public static native void link(Class<?> wrapper,/*class with native method*/
                                   String symbol,/*method name*/
                                   byte argumentSize,/*number of arguments for the method*/
                                   String jniSignature,/*jni method signature*/
                                   long ffiCallInterface/*ffi call interface*/);
    /*
     * <- linker
     */
}
