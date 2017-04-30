package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static org.freedesktop.jaccall.Size.sizeof;


final class PointerCLong extends Pointer<CLong> {

    PointerCLong(final long address,
                 final boolean autoFree) {
        super(CLong.class,
              address,
              autoFree,
              sizeof((CLong) null));
    }

    @Nonnull
    @Override
    public CLong get() {
        return get(0);
    }

    @Nonnull
    @Override
    public CLong get(@Nonnegative final int index) {
        return new CLong(JNI.getCLong(this.address,
                                      index));
    }

    @Override
    public void set(@Nonnull final CLong val) {
        set(0,
            val);
    }

    @Override
    public void set(@Nonnegative final int index,
                    @Nonnull final CLong val) {
        JNI.setCLong(this.address,
                     index,
                     val.longValue());
    }
}
