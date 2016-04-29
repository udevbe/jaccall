package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static org.freedesktop.jaccall.Size.sizeof;


final class PointerDouble extends Pointer<Double> {
    PointerDouble(final long address,
                  final boolean autoFree) {
        super(Double.class,
              address,
              autoFree,
              Size.sizeof((Double) null));
    }

    @Override
    public Double dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public Double dref(@Nonnegative final int index) {
        return JNI.readDouble(this.address,
                              index);
    }

    @Override
    public void write(@Nonnull final Double val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Double val) {
        JNI.writeDouble(this.address,
                        index,
                        val);
    }

    void write(final double[] val) {
        JNI.writeDoubles(this.address,
                         val);
    }
}
