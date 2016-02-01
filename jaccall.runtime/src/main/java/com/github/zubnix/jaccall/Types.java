package com.github.zubnix.jaccall;

import javax.annotation.Nullable;

public class Types {

    public static int newOffset(final int alignment,
                                final int offset) {
        return (offset + alignment - 1) & ~(alignment - 1);
    }

    public static int alignment(@Nullable final Byte b) { return 1; }

    public static int alignment(@Nullable final Character b) { return 2; }

    public static int alignment(@Nullable final Short b) { return 2; }

    public static int alignment(@Nullable final Integer b) { return 4; }

    public static int alignment(@Nullable final CLong cLong) { return platformAlignment(); }

    public static int alignment(@Nullable final Long b) { return platformAlignment(); }

    public static int alignment(@Nullable final Pointer<?> pointer) { return platformAlignment(); }

    public static int alignment(@Nullable final Float b) { return 4; }

    public static int alignment(@Nullable final Double b) { return platformAlignment(); }

    private static int platformAlignment() {
        //assume pointer size corresponds to memory cycle size
        return Size.sizeof((Pointer) null);
    }
}
