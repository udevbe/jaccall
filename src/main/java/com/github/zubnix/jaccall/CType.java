package com.github.zubnix.jaccall;

import javax.annotation.Nonnull;

import static com.github.zubnix.jaccall.Size.sizeof;

public enum CType {
    CHAR(sizeof((Byte) null),
         byte.class),
    UNSIGNED_CHAR(sizeof((Byte) null),
                  byte.class),

    SHORT(sizeof((Short) null),
          short.class),
    UNSIGNED_SHORT(sizeof((Short) null),
                   short.class),

    INT(sizeof((Integer) null),
        int.class),
    UNSIGNED_INT(sizeof((Integer) null),
                 int.class),

    LONG(sizeof((CLong) null),
         long.class),
    UNSIGNED_LONG(sizeof((CLong) null),
                  long.class),

    LONG_LONG(sizeof((Long) null),
              long.class),
    UNSIGNED_LONG_LONG('L',
                       long.class),

    FLOAT(sizeof((Float) null),
          float.class),

    DOUBLE(sizeof((Double) null),
           double.class),

    /**
     * Any pointer type.
     */
    POINTER(sizeof((Pointer<Void>) null),
            Void.class),

    /**
     * A struct or union by value.
     */
    STRUCT(-1,
           StructType.class);

    private final int      size;
    @Nonnull
    private final Class<?> javaType;

    CType(final int size,
          @Nonnull final Class<?> javaType) {
        this.size = size;
        this.javaType = javaType;
    }

    public int getSize() {
        return this.size;
    }

    @Nonnull
    public Class<?> getJavaType() {
        return javaType;
    }
}
