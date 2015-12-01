package com.github.zubnix.jaccall;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A field of a {@link Struct} or {@link Union}.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Field {
    /**
     * The data type defined by this field.
     *
     * @return The C type that defines this type.
     */
    CType type();

    /**
     * Define an embedded struct or union.
     * <p>
     * Will only be interpreted in conjunction with {@link #type()} set to
     * {@link CType#STRUCT} or {@link CType#UNION}.
     *
     * @return A class annotated with {@link Struct}. If no annotation is found a compile time error is thrown.
     */
    Class<?> dataType() default Void.class;

    /**
     * The name of the type.
     *
     * @return A name to identify a type. A name should be unique within a struct or type.
     */
    String name();
}
