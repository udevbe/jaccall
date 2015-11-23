package com.github.zubnix.jdyncall.api;


public @interface Field {
    FieldType type();
    Class<?> embed() default Object.class;
    String name();
}
