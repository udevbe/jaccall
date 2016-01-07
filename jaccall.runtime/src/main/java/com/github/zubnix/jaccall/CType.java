package com.github.zubnix.jaccall;

public enum CType {
    CHAR,
    UNSIGNED_CHAR,

    SHORT,
    UNSIGNED_SHORT,

    INT,
    UNSIGNED_INT,

    LONG,
    UNSIGNED_LONG,

    LONG_LONG,
    UNSIGNED_LONG_LONG,

    FLOAT,
    DOUBLE,

    /**
     * Any pointer type.
     */
    POINTER,

    /**
     * A struct or union by value.
     */
    STRUCT;
}
