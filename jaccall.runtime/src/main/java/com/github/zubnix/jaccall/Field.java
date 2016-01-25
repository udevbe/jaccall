package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A field of a {@link Struct} or {@link Union}.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
    /**
     * The data type defined by this field.
     *
     * @return The C type that defines this type.
     */
    CType type();

    /**
     * Define an embedded struct or union.
     * <p/>
     * Will only be interpreted in conjunction with {@link #type()} set to
     * {@link CType#STRUCT} or {@link CType#POINTER}.
     *
     * @return A class annotated with {@link Struct}. If no annotation is found a compile time error is thrown.
     */
    Class<?> dataType() default Void.class;

    /**
     * Define the depth of pointer-to-pointer field types. A pointer that does not references a pointer (void*) has a
     * depth of 0, a pointer that references a  pointer (void**) has a depth of 1, a pointer-to-pointer-to-pointer
     * (void***) has a depth of 2 etc.
     * <p/>
     * Will only be interpreted in conjunction with {@link #type()} set to {@link CType#POINTER}.
     *
     * @return The pointer-to-pointer depth.
     */
    @Nonnegative
    int pointerDepth() default 0;

    /**
     * The name of the field.
     *
     * @return A name to identify a field. A name should be unique within a struct or type.
     */
    String name();

    /**
     * The number of times the type of this field repeats. The cardinality is used to define the length of a nested
     * array of type: {@link #type()}. When the cardinality is greater than one, the field will be represented as a pointer.
     * <p/>
     * A cardinality of < 1 will result in a compile time error.
     *
     * @return the number of times the the type of this field repeats inside the struct. Defaults to 1.
     */
    @Nonnegative
    int cardinality() default 1;
}
