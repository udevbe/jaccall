package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;


final class PointerDouble extends Pointer<Double> {
    PointerDouble(final long address,
                  final boolean autoFree) {
        super(Double.class,
              address,
              autoFree,
              Size.sizeof((Double) null));
    }

    @Override
    public Double get() {
        return get(0);
    }

    @Nonnull
    @Override
    public Double get(@Nonnegative final int index) {
        return JNI.getDouble(this.address,
                              index);
    }

    @Override
    public void set(@Nonnull final Double val) {
        set(0,
            val);
    }

    @Override
    public void set(@Nonnegative final int index,
                    @Nonnull final Double val) {
        JNI.setDouble(this.address,
                        index,
                        val);
    }

    void set(final double[] val) {
        JNI.setDoubles(this.address,
                         val);
    }
}
