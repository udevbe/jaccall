package com.github.zubnix.jaccall;

import javax.annotation.Nonnull;

public enum CType {
    CHAR(byte.class),
    UNSIGNED_CHAR(byte.class),

    SHORT(short.class),
    UNSIGNED_SHORT(short.class),

    INT(int.class),
    UNSIGNED_INT(int.class),

    LONG(long.class),
    UNSIGNED_LONG(long.class),

    LONG_LONG(long.class),
    UNSIGNED_LONG_LONG(long.class),

    FLOAT(float.class),

    DOUBLE(double.class),

    /**
     * Any pointer type.
     */
    POINTER(Void.class),

    /**
     * A struct or union by value.
     */
    STRUCT(StructType.class);

    @Nonnull
    private final Class<?> javaType;

    CType(@Nonnull final Class<?> javaType) {
        this.javaType = javaType;
    }

    @Nonnull
    public Class<?> getJavaType() {
        return this.javaType;
    }
}
