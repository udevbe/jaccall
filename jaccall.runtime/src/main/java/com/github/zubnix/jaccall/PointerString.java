package com.github.zubnix.jaccall;


import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

import static com.github.zubnix.jaccall.Size.sizeof;

class PointerString extends Pointer<String> {

    private static final CharsetEncoder CHARSET_ENCODER = StandardCharsets.US_ASCII.newEncoder();

    PointerString(final Type type,
                  final long address,
                  @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer,
              sizeof((Byte) null));
    }

    @Override
    String dref(@Nonnull final ByteBuffer byteBuffer) {
        return dref(0,
                    byteBuffer);
    }

    @Override
    String dref(@Nonnegative final int index,
                @Nonnull final ByteBuffer byteBuffer) {
        byteBuffer.position(index);
        final StringBuilder sb = new StringBuilder(byteBuffer.limit());
        while (byteBuffer.remaining() > 0) {
            final char c = (char) byteBuffer.get();
            if (c == '\0') {
                break;
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public void write(@Nonnull final String val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final String val) {
        this.byteBuffer.position(index);
        CHARSET_ENCODER.encode(CharBuffer.wrap(val),
                               this.byteBuffer,
                               true);
        this.byteBuffer.put((byte) 0);
    }
}
