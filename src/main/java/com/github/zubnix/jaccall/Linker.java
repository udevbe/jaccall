package com.github.zubnix.jaccall;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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
                                    final Class<?> aClass) {
        final Method[] methods = aClass.getMethods();


        for (Method method : methods) {
            final int modifiers = method.getModifiers();
            if (Modifier.isNative(modifiers) &&
                Modifier.isStatic(modifiers)) {

                final String symbol = method.getName();
                final long address = JNI.sym(handle,
                                             symbol);
                final String signature = getFFISignature(method);

                //TODO create method signatures for all methods and pass that to native side to setup libffi closures
            }
        }
    }

    //TODO this is something we can infer at compile time but how to get to it at runtime?
    private static String getFFISignature(final Method method) {
        final Class<?>     returnType            = method.getReturnType();
        final Annotation[] returnTypeAnnotations = method.getAnnotations();
        //TODO checking if ByVal annotation is done on a method that returns `long` should be done at compile time.

        final Class<?>[]     parameterTypes       = method.getParameterTypes();
        final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        //TODO checking if any ByVal annotation is done on a method parameter of type `long` should be done at compile time.

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parameterTypes.length; i++) {
            sb.append(typeToSignature(parameterTypes[i],
                                      parameterAnnotations[i]));
        }
        sb.append(')')
          .append(typeToSignature(returnType,
                                  returnTypeAnnotations));


        return sb.toString();
    }

    private static char typeToSignature(Class<?> type,
                                        final Annotation[] parameterAnnotation) {
//        'B'	bool
//        'c'	char
//        'C'	unsigned char
//        's'	short
//        'S'	unsigned short
//        'i'	int
//        'I'	unsigned int
//        'j'	long
//        'J'	unsigned long
//        'l'	long long
//        'L'	unsigned long long
//        'f'	float
//        'd'	double
//        'p'	C pointer
//        'Z'	char*
//        'x'	SEXP
//        'v'	void

        if (type.equals(Integer.class) || type.equals(int.class)) {
            return 'i';
        }

        if (type.equals(Float.class) || type.equals(float.class)) {
            return 'f';
        }

        if (type.equals(Byte.class) || type.equals(byte.class)) {
            return 'c';
        }

        if (type.equals(Short.class) ||
            type.equals(short.class) ||
            type.equals(Character.class) ||
            type.equals(char.class)) {
            return 's';
        }

        if (type.equals(Long.class) || type.equals(long.class)) {
            for (Annotation annotation : parameterAnnotation) {
                if(annotation instanceof Ptr){
                    return 'p';
                }
                if(annotation instanceof ByVal){
                    return 't';
                }
            }


            return ' ';
        }

        if (type.equals(Double.class) || type.equals(double.class)) {
            return 'd';
        }

        if (type.equals(CLong.class)) {
            return 'l';
        }

        throw new IllegalArgumentException("Type " + type + " does not have a known native representation.");
    }
}
