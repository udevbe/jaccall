package com.github.zubnix.jaccall;


import java.lang.annotation.Annotation;

public final class Linker {

    public static void link(Class<?>... lib) {
        for (Class<?> aClass : lib) {
            final Lib libAnnotation = aClass.getAnnotation(Lib.class);
            if (libAnnotation != null) {
                final String libName = libAnnotation.value();
                link(aClass,
                     "lib" + libName + ".so");
            }
        }
    }

    public static void link(Class<?> lib,
                            String nativeLibPath) {
        final long handle = JNI.open(nativeLibPath);
        linkSymbols(handle,
                    lib);
    }

    private static void linkSymbols(final long handle,
                                    final Class<?> lib) {
        //find method signatures and symbol name from generated LinkSymbols class
        try {
            LinkSymbols linkSymbols = (LinkSymbols) lib.getClassLoader()
                                                       .loadClass(lib.getName() +
                                                                  "_Jaccall_" +
                                                                  LinkSymbols.class.getSimpleName())
                                                       .newInstance();
            final String[] symbols = linkSymbols.symbols();
            final String[] signatures = linkSymbols.signatures();
            for (int i = 0; i < symbols.length; i++) {
                final String symbol = symbols[i];
                final String signature = signatures[i];
                final long symbolAddress = JNI.sym(handle,
                                                   symbol);
            }


        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    //TODO move this to linksymbols generator
    private static String typeToSignature(Class<?> type,
                                          final Annotation[] parameterAnnotation) {
        //C to Java mapping
//        'c'	char -> Byte, byte
//        's'	short -> Character, char, Short, short
//        'i'	int -> Integer, int
//        'j'	long -> CLong
//        'l'	long long -> Long, long
//        'f'	float -> Float, float
//        'd'	double -> Double, double
//        'p'	C pointer -> @Ptr Long, @Ptr long
//        'v'	void -> Void, void
//        't...]'   struct -> @ByVal(SomeStruct.class) Long, @ByVal(SomeStruct.class) long,

        if (type.equals(Integer.class) || type.equals(int.class)) {
            return "i";
        }

        if (type.equals(Long.class) || type.equals(long.class)) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof Ptr) {
                    return "p";
                }
                if (annotation instanceof ByVal) {
                    ByVal byVal = (ByVal) annotation;
                    //TODO do we really need the struct signature?
                    return "t";
                }
            }
            return "l";
        }

        if (type.equals(Float.class) || type.equals(float.class)) {
            return "f";
        }

        if (type.equals(Byte.class) || type.equals(byte.class)) {
            return "c";
        }

        if (type.equals(Short.class) ||
            type.equals(short.class) ||
            type.equals(Character.class) ||
            type.equals(char.class)) {
            return "s";
        }

        if (type.equals(Double.class) || type.equals(double.class)) {
            return "d";
        }

        if (type.equals(CLong.class)) {
            return "j";
        }

        throw new IllegalArgumentException("Type " + type + " does not have a known native representation.");
    }
}
