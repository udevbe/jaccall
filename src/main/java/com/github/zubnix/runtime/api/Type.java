package com.github.zubnix.runtime.api;

public enum Type {
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
            long.class),

    /**
     * A struct by value.
     */
    STRUCT('x',
           Object.class),

    /**
     * A union by value.
     */
    UNION('y',
          Object.class);


    //LONG_DOUBLE;

    private final byte     signature;
    private final Class<?> javaType;

    Type(final char signature,
         final Class<?> javaType) {
        this.signature = (byte) signature;
        this.javaType = javaType;
    }

    public int getSignature() {
        return signature;
    }

    public Class<?> getJavaType() {
        return javaType;
    }
}
