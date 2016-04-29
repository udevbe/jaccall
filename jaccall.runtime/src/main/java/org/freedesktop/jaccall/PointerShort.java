package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static org.freedesktop.jaccall.Size.sizeof;

final class PointerShort extends Pointer<Short> {
    PointerShort(final long address,
                 final boolean autoFree) {
        super(Short.class,
              address,
              autoFree,
              Size.sizeof((Short) null));
    }

    @Nonnull
    @Override
    public Short dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public Short dref(@Nonnegative final int index) {
        return JNI.readShort(this.address,
                             index);
    }

    @Override
    public void write(@Nonnull final Short val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Short val) {
        JNI.writeShort(this.address,
                       index,
                       val);
    }

    void write(final short[] val) {
        JNI.writeShorts(this.address,
                        val);
    }
}
