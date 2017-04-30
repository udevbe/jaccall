package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;


final class PointerInt extends Pointer<Integer> {
    PointerInt(final long address,
               final boolean autoFree) {
        super(Integer.class,
              address,
              autoFree,
              Size.sizeof((Integer) null));
    }

    @Override
    public Integer get() {
        return get(0);
    }

    @Nonnull
    @Override
    public Integer get(@Nonnegative final int index) {
        return JNI.getInt(this.address,
                          index);
    }

    @Override
    public void set(@Nonnull final Integer val) {
        set(0,
            val);
    }

    @Override
    public void set(@Nonnegative final int index,
                    @Nonnull final Integer val) {
        JNI.setInt(this.address,
                   index,
                   val);
    }

    void set(final int[] val) {
        JNI.setInts(this.address,
                    val);
    }
}
