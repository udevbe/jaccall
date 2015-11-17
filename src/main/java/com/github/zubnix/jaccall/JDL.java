package com.github.zubnix.jaccall;


import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class JDL {

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
                            String filename) {
        final long handle = JNI.open(filename);
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

                //TODO create method signatures for all methods and pass that to native side to setup libffi closures
            }
        }
    }
}
