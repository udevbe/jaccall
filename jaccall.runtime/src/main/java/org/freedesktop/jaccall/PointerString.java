package org.freedesktop.jaccall;


import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

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
    public String get() {
        return get(0);
    }

    @Nonnull
    @Override
    public String get(@Nonnegative final int index) {
        return JNI.getString(this.address,
                             index);
    }

    @Override
    public void set(@Nonnull final String val) {
        set(0,
            val);
    }

    @Override
    public void set(@Nonnegative final int index,
                    @Nonnull final String val) {
        JNI.setString(this.address,
                      index,
                      val);
    }
}
