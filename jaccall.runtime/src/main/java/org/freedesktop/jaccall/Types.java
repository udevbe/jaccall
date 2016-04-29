package org.freedesktop.jaccall;

import javax.annotation.Nullable;

public class Types {

    public static int newOffset(final int alignment,
                                final int offset) {
        return (offset + alignment - 1) & ~(alignment - 1);
    }

    public static int alignment(@Nullable final Byte b) { return JNI.CHAR_ALIGNMENT; }

    public static int alignment(@Nullable final Short b) { return JNI.SHORT_ALIGNMENT; }

    public static int alignment(@Nullable final Integer b) { return JNI.INT_ALIGNMENT; }

    public static int alignment(@Nullable final CLong cLong) { return JNI.LONG_ALIGNMENT; }

    public static int alignment(@Nullable final Long b) { return JNI.LONG_LONG_ALIGNMENT; }

    public static int alignment(@Nullable final Pointer<?> pointer) { return JNI.POINTER_ALIGNMENT; }

    public static int alignment(@Nullable final Float b) { return JNI.FLOAT_ALIGNMENT; }

    public static int alignment(@Nullable final Double b) { return JNI.DOUBLE_ALIGNMENT; }
}
