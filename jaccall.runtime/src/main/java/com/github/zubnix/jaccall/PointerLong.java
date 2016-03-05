package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static com.github.zubnix.jaccall.Size.sizeof;


final class PointerLong extends Pointer<Long> {
    PointerLong(final long address,
                final boolean autoFree) {
        super(Long.class,
              address,
              autoFree,
              sizeof((Long) null));
    }

    @Override
    public Long dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public Long dref(@Nonnegative final int index) {
        return JNI.readLong(this.address,
                            index);
    }

    @Override
    public void write(@Nonnull final Long val) {
        writei(0,
               val);
    }

    public void writei(@Nonnegative final int index,
                       @Nonnull final Long val) {
        JNI.writeLong(this.address,
                      index,
                      val);
    }

    void write(final long[] val) {
        JNI.writeLongs(this.address,
                       val);
    }
}
