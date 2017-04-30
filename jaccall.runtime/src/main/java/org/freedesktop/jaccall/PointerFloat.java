package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static org.freedesktop.jaccall.Size.sizeof;

final class PointerFloat extends Pointer<Float> {
    PointerFloat(final long address,
                 final boolean autoFree) {
        super(Float.class,
              address,
              autoFree,
              sizeof((Float) null));
    }

    @Override
    public Float get() {
        return get(0);
    }

    @Nonnull
    @Override
    public Float get(@Nonnegative final int index) {
        return JNI.getFloat(this.address,
                            index);
    }

    @Override
    public void set(@Nonnull final Float val) {
        set(0,
            val);
    }

    @Override
    public void set(@Nonnegative final int index,
                    @Nonnull final Float val) {
        JNI.setFloat(this.address,
                     index,
                     val);
    }

    void set(final float[] val) {
        JNI.setFloats(this.address,
                      val);
    }
}
