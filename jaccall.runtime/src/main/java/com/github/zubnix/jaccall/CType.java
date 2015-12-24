package com.github.zubnix.jaccall;

import javax.annotation.Nonnull;

import static com.github.zubnix.jaccall.Size.sizeof;

public enum CType {
    CHAR(sizeof((Byte) null),
         byte.class,
         'c'),
    UNSIGNED_CHAR(sizeof((Byte) null),
                  byte.class,
                  'C'),

    SHORT(sizeof((Short) null),
          short.class,
          's'),
    UNSIGNED_SHORT(sizeof((Short) null),
                   short.class,
                   'S'),

    INT(sizeof((Integer) null),
        int.class,
        'i'),
    UNSIGNED_INT(sizeof((Integer) null),
                 int.class,
                 'I'),

    LONG(sizeof((CLong) null),
         long.class,
         'j'),
    UNSIGNED_LONG(sizeof((CLong) null),
                  long.class,
                  'J'),

    LONG_LONG(sizeof((Long) null),
              long.class,
              'l'),
    UNSIGNED_LONG_LONG(sizeof((Long) null),
                       long.class,
                       'L'),

    FLOAT(sizeof((Float) null),
          float.class,
          'f'),

    DOUBLE(sizeof((Double) null),
           double.class,
           'd'),

    /**
     * Any pointer type.
     */
    POINTER(sizeof((Pointer<Void>) null),
            Void.class,
            'p'),

    /**
     * A struct or union by value.
     */
    STRUCT(-1,
           StructType.class,
           't');

    //fields used at compile time
    private final int      size;
    @Nonnull
    private final Class<?> javaType;
    private final char     signature;

    CType(final int size,
          @Nonnull final Class<?> javaType,
          final char signature) {
        this.size = size;
        this.javaType = javaType;
        this.signature = signature;
    }

    public int getSize() {
        return this.size;
    }

    @Nonnull
    public Class<?> getJavaType() {
        return this.javaType;
    }

    public char getSignature() {
        return this.signature;
    }
}
