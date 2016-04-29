package org.freedesktop.jaccall;


public class Symbols {

    private static final String OS_NAME = System.getProperty("os.name")
                                                .toLowerCase();

    private static final String OS_WINDOWS = "windows";
    private static final String OS_LINUX   = "linux";
    private static final String OS_MAC     = "mac";
    private static final String OS_FREEBSD = "freebsd";

    private final Class<?> javaLibrary;
    private final String[] symbols;
    private final byte[]   argumentSizes;
    private final long[]   ffiCallInterfaces;
    private final String[] jniSignatures;

    public Symbols(final Class<?> javaLibrary,
                   final String[] symbols,
                   final byte[] argumentSizes,
                   final long[] ffiCallInterfaces,
                   final String[] jniSignatures) {
        this.javaLibrary = javaLibrary;
        this.symbols = symbols;
        this.argumentSizes = argumentSizes;
        this.ffiCallInterfaces = ffiCallInterfaces;
        this.jniSignatures = jniSignatures;
    }

    public void link() {
        link(nativeLibraryPath());
    }

    public void link(final String nativeLibraryPath) {
        JNI.link(nativeLibraryPath,
                 this.javaLibrary,
                 this.symbols,
                 this.argumentSizes,
                 this.jniSignatures,
                 this.ffiCallInterfaces);
    }

    private String nativeLibraryPath() {
        final Lib    libAnnotation = this.javaLibrary.getAnnotation(Lib.class);
        final String libName       = libAnnotation.value();

        final String osLibName;
        switch (OS_NAME) {
            case OS_LINUX: {
                osLibName = "lib" + libName + ".so";
                break;
            }
            case OS_MAC: {
                //not supported.
            }
            case OS_WINDOWS: {
                //not supported.
            }
            case OS_FREEBSD: {
                //not supported.
            }
            default: {
                throw new UnsupportedOperationException(String.format("Operating system %s not supported.",
                                                                      OS_NAME));
            }
        }

        return osLibName;
    }
}
