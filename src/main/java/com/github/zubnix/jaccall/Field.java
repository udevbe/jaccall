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
     * The number of times the type of this field repeats. The cardinality is used to define the length of a nested
     * array of type: {@link #type()}.
     *
     * @return the number of times the the type of this field repeats inside the struct. Defaults to 1.
     */
    int cardinal() default 1;

    /**
     * Define an embedded struct or union.
     * <p/>
     * Will only be interpreted in conjunction with {@link #type()} set to
     * {@link CType#STRUCT}.
     *
     * @return A class annotated with {@link Struct}. If no annotation is found a compile time error is thrown.
     */
    Class<?> dataType() default Void.class;

    /**
     * The name of the field.
     *
     * @return A name to identify a field. A name should be unique within a struct or type.
     */
    String name();
}
