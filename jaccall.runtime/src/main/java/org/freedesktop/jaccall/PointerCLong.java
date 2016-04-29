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
    public CLong dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public CLong dref(@Nonnegative final int index) {
        return new CLong(JNI.readCLong(this.address,
                                       index));
    }

    @Override
    public void write(@Nonnull final CLong val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final CLong val) {
        JNI.writeCLong(this.address,
                       index,
                       val.longValue());
    }
}
