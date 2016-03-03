package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static com.github.zubnix.jaccall.Size.sizeof;

final class PointerByte extends Pointer<Byte> {

    PointerByte(final long address) {
        super(Byte.class,
              address,
              sizeof((Byte) null));
    }

    @Nonnull
    @Override
    public Byte dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public Byte dref(@Nonnegative final int index) {
        return JNI.drefByte(address,
                            index);
    }

    @Override
    public void write(@Nonnull final Byte val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Byte val) {
        JNI.writeByte(address,
                      index,
                      val.byteValue());
    }

    void write(final byte[] val) {
        JNI.writeBytes(address,
                       val);
    }
}
