package com.github.zubnix.jaccall;

import javax.annotation.Nonnull;

public enum CType {
    //type signatures match dyncall arg signature, except for struct and union.

    CHAR('c',
         byte.class),
    UNSIGNED_CHAR('C',
                  byte.class),

    SHORT('s',
          short.class),
    UNSIGNED_SHORT('S',
                   short.class),

    INT('i',
        int.class),
    UNSIGNED_INT('I',
                 int.class),

    LONG('j',
         long.class),
    UNSIGNED_LONG('J',
                  long.class),

    LONG_LONG('l',
              long.class),
    UNSIGNED_LONG_LONG('L',
                       long.class),

    FLOAT('f',
          float.class),

    DOUBLE('d',
           double.class),

    /**
     * Any pointer type.
     */
    POINTER('p',
            Void.class),

    /**
     * A struct by value.
     */
    STRUCT('x',
           StructType.class),

    /**
     * A union by value.
     */
    UNION('y',
          StructType.class);

    private final byte     signature;
    @Nonnull
    private final Class<?> javaType;

    CType(final char signature,
          @Nonnull final Class<?> javaType) {
        this.signature = (byte) signature;
        this.javaType = javaType;
    }

    public int getSignature() {
        return signature;
    }

    @Nonnull
    public Class<?> getJavaType() {
        return javaType;
    }
}