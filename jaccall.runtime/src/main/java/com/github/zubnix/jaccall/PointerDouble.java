package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static com.github.zubnix.jaccall.Size.sizeof;


final class PointerDouble extends Pointer<Double> {
    PointerDouble(final long address) {
        super(Double.class,
              address,
              sizeof((Double) null));
    }

    @Override
    public Double dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public Double dref(@Nonnegative final int index) {
        return JNI.drefDouble(address,
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
        JNI.writeDouble(address,
                        index,
                        val.doubleValue());
    }

    void write(final double[] val) {
        JNI.writeDoubles(address,
                         val);
    }
}
