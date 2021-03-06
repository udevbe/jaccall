package org.freedesktop.jaccall;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Size {

    private Size() {
    }

    private static final int POINTER_SIZE = JNI.sizeOfPointer();
    private static final int CLONG_SIZE   = JNI.sizeOfCLong();

    public static int sizeof(@Nullable final Byte b) { return 1; }

    public static int sizeof(final byte b) { return sizeof((Byte) null); }

    public static int sizeof(@Nullable final Character b) { return 2; }

    public static int sizeof(final char b) { return sizeof((Character) null); }

    public static int sizeof(@Nullable final Short b) { return 2; }

    public static int sizeof(final short b) { return sizeof((Short) null); }

    public static int sizeof(@Nullable final Integer b) { return 4; }

    public static int sizeof(final int b) { return sizeof((Integer) null); }

    public static int sizeof(@Nullable final Float b) { return 4; }

    public static int sizeof(final float b) { return sizeof((Float) null); }

    public static int sizeof(@Nullable final Long b) { return 8; }

    public static int sizeof(final long b) { return sizeof((Long) null); }

    public static int sizeof(@Nullable final Double b) { return 8; }

    public static int sizeof(final double b) { return sizeof((Double) null); }

    public static int sizeof(@Nullable final Pointer<?> pointer) { return POINTER_SIZE; }

    public static int sizeof(@Nullable final CLong cLong) { return CLONG_SIZE; }

    public static int sizeof(@Nonnull final String val) { return val.length() + 1; }

    public static int sizeof(@Nullable final Void val) { throw new IllegalArgumentException(); }

    public static int sizeof(@Nonnull final StructType structType) { return structType.size; }
}
