package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static com.github.zubnix.jaccall.Size.sizeof;


final class PointerInt extends Pointer<Integer> {
    PointerInt(final long address,
               final boolean autoFree) {
        super(Integer.class,
              address,
              autoFree,
              sizeof((Integer) null));
    }

    @Override
    public Integer dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public Integer dref(@Nonnegative final int index) {
        return JNI.readInt(this.address,
                           index);
    }

    @Override
    public void write(@Nonnull final Integer val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Integer val) {
        JNI.writeInt(this.address,
                     index,
                     val.intValue());
    }

    void write(final int[] val) {
        JNI.writeInts(this.address,
                      val);
    }
}
