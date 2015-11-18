package com.github.zubnix.jaccall;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface ByVal {
    Class<? extends StructType> value();
}
