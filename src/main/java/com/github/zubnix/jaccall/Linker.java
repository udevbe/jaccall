package com.github.zubnix.jaccall;


public final class Linker {

    private static String POSTFIX = "_Jaccall_" + LinkSymbols.class.getSimpleName();

    public static void link(Class<?>... headers) {
        try {
            for (Class<?> header : headers) {
                final Lib libAnnotation = header.getAnnotation(Lib.class);
                if (libAnnotation != null) {
                    LinkSymbols linkSymbols = (LinkSymbols) header.getClassLoader()
                                                                  .loadClass(header.getName() +
                                                                             POSTFIX)
                                                                  .newInstance();
                    final String libName = libAnnotation.value();
                    //TODO libname resolving is platform depended
                    link(header,
                         "lib" + libName + ".so",
                         linkSymbols);

                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public static void link(Class<?> header,
                            String absoluteLibPath,
                            final LinkSymbols linkSymbols) {

        final String[] symbols       = linkSymbols.symbols();
        final int      nroSymbols    = symbols.length;
        final long     handle        = JNI.open(absoluteLibPath);
        final long[]   symbolAddress = new long[nroSymbols];

        for (int i = 0; i < nroSymbols; i++) {
            symbolAddress[i] = JNI.sym(handle,
                                       symbols[i]);
        }

        JNI.link(header,
                 nroSymbols,
                 symbols,
                 linkSymbols.jniSignatures(),
                 linkSymbols.jaccallSignatures(),
                 handle,
                 symbolAddress);
    }
}
