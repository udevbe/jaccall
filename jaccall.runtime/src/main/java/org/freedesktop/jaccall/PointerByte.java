package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

final class PointerByte extends Pointer<Byte> {

    PointerByte(final long address,
                final boolean autoFree) {
        super(Byte.class,
              address,
              autoFree,
              Size.sizeof((Byte) null));
    }

    @Nonnull
    @Override
    public Byte get() {
        return get(0);
    }

    @Nonnull
    @Override
    public Byte get(@Nonnegative final int index) {
        return JNI.getByte(this.address,
                           index);
    }

    @Override
    public void set(@Nonnull final Byte val) {
        set(0,
            val);
    }

    @Override
    public void set(@Nonnegative final int index,
                    @Nonnull final Byte val) {
        JNI.setByte(this.address,
                    index,
                    val);
    }

    void set(final byte[] val) {
        JNI.setBytes(this.address,
                     val);
    }
}
