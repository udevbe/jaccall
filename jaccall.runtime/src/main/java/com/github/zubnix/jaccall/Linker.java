package com.github.zubnix.jaccall;


public final class Linker {

    private static final String POSTFIX = "_Jaccall_" + LinkSymbols.class.getSimpleName();

    public static void link(final Class<?>... headers) {
        try {
            for (final Class<?> header : headers) {
                final Lib libAnnotation = header.getAnnotation(Lib.class);
                if (libAnnotation != null) {
                    final LinkSymbols linkSymbols = (LinkSymbols) header.getClassLoader()
                                                                        .loadClass(header.getName() +
                                                                                   POSTFIX)
                                                                        .newInstance();
                    final String libName = libAnnotation.value();
                    //TODO libname resolving is platform depended
                    link("lib" + libName + ".so",
                         header,
                         linkSymbols);

                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public static void link(final String libraryPath,
                            final Class<?> header,
                            final LinkSymbols linkSymbols) {

        JNI.link(libraryPath,
                 header,
                 linkSymbols.symbols(),
                 linkSymbols.argumentSizes(),
                 linkSymbols.jniSignatures(),
                 linkSymbols.jaccallSignatures());
    }
}
