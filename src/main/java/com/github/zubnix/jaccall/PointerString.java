package com.github.zubnix.jaccall;


import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

class PointerString extends Pointer<String> {

    private static final CharsetEncoder CHARSET_ENCODER = StandardCharsets.US_ASCII.newEncoder();

    PointerString(final Type type,
                  final long address,
                  @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
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
            char c = (char) byteBuffer.get();
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
    void write(@Nonnull final ByteBuffer byteBuffer,
               @Nonnull final String... val) {
        writei(byteBuffer,
               0,
               val);
    }

    @Override
    void writei(@Nonnull final ByteBuffer byteBuffer,
                @Nonnegative final int index,
                final String... val) {
        byteBuffer.position(index);
        for (String s : val) {
            CHARSET_ENCODER.encode(CharBuffer.wrap(s),
                                   byteBuffer,
                                   true);
            byteBuffer.put((byte) 0);
        }
    }
}
