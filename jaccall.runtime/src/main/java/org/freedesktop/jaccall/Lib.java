package org.freedesktop.jaccall;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Lib {
    /**
     * Name of the library with the lib prefix.
     *
     * @return
     */
    String value();

    /**
     * Major ABI version number of the library. Ie in Libfoo.so.7.6.5 , the number 7 would be the major version number.
     *
     * @return
     */
    int version() default 0;
}
