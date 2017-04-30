package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;


final class PointerLong extends Pointer<Long> {
    PointerLong(final long address,
                final boolean autoFree) {
        super(Long.class,
              address,
              autoFree,
              Size.sizeof((Long) null));
    }

    @Override
    public Long get() {
        return get(0);
    }

    @Nonnull
    @Override
    public Long get(@Nonnegative final int index) {
        return JNI.getLong(this.address,
                           index);
    }

    @Override
    public void set(@Nonnull final Long val) {
        set(0,
            val);
    }

    public void set(@Nonnegative final int index,
                    @Nonnull final Long val) {
        JNI.setLong(this.address,
                    index,
                    val);
    }

    void set(final long[] val) {
        JNI.setLongs(this.address,
                     val);
    }
}
