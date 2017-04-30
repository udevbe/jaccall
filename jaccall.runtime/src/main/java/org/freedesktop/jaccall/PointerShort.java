package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

final class PointerShort extends Pointer<Short> {
    PointerShort(final long address,
                 final boolean autoFree) {
        super(Short.class,
              address,
              autoFree,
              Size.sizeof((Short) null));
    }

    @Nonnull
    @Override
    public Short get() {
        return get(0);
    }

    @Nonnull
    @Override
    public Short get(@Nonnegative final int index) {
        return JNI.getShort(this.address,
                            index);
    }

    @Override
    public void set(@Nonnull final Short val) {
        set(0,
            val);
    }

    @Override
    public void set(@Nonnegative final int index,
                    @Nonnull final Short val) {
        JNI.setShort(this.address,
                     index,
                     val);
    }

    void set(final short[] val) {
        JNI.setShorts(this.address,
                      val);
    }
}
