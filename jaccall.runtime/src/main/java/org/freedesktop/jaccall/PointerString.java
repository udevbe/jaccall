package org.freedesktop.jaccall;


import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

import static org.freedesktop.jaccall.Size.sizeof;

final class PointerString extends Pointer<String> {

    private static final CharsetEncoder CHARSET_ENCODER = StandardCharsets.US_ASCII.newEncoder();

    PointerString(final long address,
                  final boolean autoFree) {
        super(String.class,
              address,
              autoFree,
              Size.sizeof((Byte) null));
    }

    @Override
    public String dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public String dref(@Nonnegative final int index) {
        return JNI.readString(this.address,
                              index);
    }

    @Override
    public void write(@Nonnull final String val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final String val) {
        JNI.writeString(this.address,
                        index,
                        val);
    }
}
