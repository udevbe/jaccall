package com.github.zubnix.runtime.api;

/**
 * A field of a {@link Struct} or {@link Union}.
 */
public @interface Field {
    /**
     * The data type defined by this field.
     *
     * @return The C type that defines this field.
     */
    Type type();

    /**
     * Define an embedded struct or union.
     * <p>
     * Will only be interpreted in conjunction with {@link #type()} set to
     * {@link Type#STRUCT} or {@link Type#UNION}.
     *
     * @return A class annotated with {@link Struct} or {@link Union}.
     * If no annotation is found a compile time error is thrown.
     */
    Class<?> embed() default Object.class;

    /**
     * The name of the field.
     *
     * @return A name to identify a field. A name should be unique within a struct or field.
     */
    String name();
}
