package com.github.zubnix.jaccall;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Ptr {
    /**
     * Programmer's aid to indicate what data the pointer is referencing.
     * This value is not interpreted in any way nor does it make any guarantee or do type checking.
     *
     * @return
     */
    Class<?> value() default Void.class;
}
