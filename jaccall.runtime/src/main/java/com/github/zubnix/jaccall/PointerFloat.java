package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static com.github.zubnix.jaccall.Size.sizeof;

final class PointerFloat extends Pointer<Float> {
    PointerFloat(final long address,
                 final boolean autoFree) {
        super(Float.class,
              address,
              autoFree,
              sizeof((Float) null));
    }

    @Override
    public Float dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public Float dref(@Nonnegative final int index) {
        return JNI.readFloat(this.address,
                             index);
    }

    @Override
    public void write(@Nonnull final Float val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Float val) {
        JNI.writeFloat(this.address,
                       index,
                       val.floatValue());
    }

    void write(final float[] val) {
        JNI.writeFloats(this.address,
                        val);
    }
}
