package com.github.zubnix.jaccall;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Func {
    CType ret() default CType.VOID;

    CType[] args() default {CType.VOID};
}
