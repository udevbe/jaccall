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
                    //TODO libname resolving is platform dependend
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

        final long     handle     = JNI.open(absoluteLibPath);
        final String[] symbols    = linkSymbols.symbols();
        final String[] signatures = linkSymbols.signatures();
        for (int i = 0; i < symbols.length; i++) {
            final String symbol = symbols[i];
            final String signature = signatures[i];
            final long symbolAddress = JNI.sym(handle,
                                               symbol);
        }
    }
}
