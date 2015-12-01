package com.github.zubnix.jaccall;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Size {

    private Size() {
    }

    private static final int POINTER_SIZE = JNI.sizeOfPointer();
    private static final int CLONG_SIZE   = JNI.sizeOfCLong();

    public static int sizeOf(@Nullable final Byte b) { return 1; }

    public static int sizeOf(final byte b) { return 1; }

    public static int sizeOf(@Nullable final Character b) { return 2; }

    public static int sizeOf(final char b) { return 2; }

    public static int sizeOf(@Nullable final Short b) { return 2; }

    public static int sizeOf(final short b) { return 2; }

    public static int sizeOf(@Nullable final Integer b) { return 4; }

    public static int sizeOf(final int b) { return 4; }

    public static int sizeOf(@Nullable final Float b) { return 4; }

    public static int sizeOf(final float b) { return 4; }

    public static int sizeOf(@Nullable final Long b) { return 8; }

    public static int sizeOf(final long b) { return 8; }

    public static int sizeOf(@Nullable final Double b) { return 8; }

    public static int sizeOf(final double b) { return 8; }

    public static int sizeOf(@Nullable final Pointer<?> pointer) { return POINTER_SIZE; }

    public static int sizeOf(@Nullable final CLong cLong) { return CLONG_SIZE; }

    public static int sizeOf(@Nullable final Void val) {
        //on gcc it's 1, but that's because it's not following the standard
        throw new IllegalArgumentException("Can not determine size of incomplete type Void.");
    }

    public static int sizeOf(@Nonnull Class<? extends StructType> structType) {

        final StructSignature structSignature = structType.getAnnotation(StructSignature.class);
        if (structSignature == null) {
            throw new IllegalArgumentException("Type does not have a StructSignature annotation.");
        }
        return JNI.dcStructSize(JNI.dcDefineStruct(structSignature.value()));
    }
}
