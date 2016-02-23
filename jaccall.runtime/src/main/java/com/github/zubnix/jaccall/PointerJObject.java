package com.github.zubnix.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import static com.github.zubnix.jaccall.Size.sizeof;

final class PointerJObject extends Pointer<JObject> {
    public PointerJObject(final long address,
                          @Nonnull final ByteBuffer byteBuffer) {
        super(JObject.class,
              address,
              byteBuffer,
              sizeof((JObject) null));
    }

    @Nonnull
    @Override
    public JObject dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public JObject dref(@Nonnegative final int index) {
        final long jobject;
        if (this.typeSize == 8) {
            final LongBuffer buffer = this.byteBuffer.asLongBuffer();
            buffer.rewind();
            buffer.position(index);
            jobject = buffer.get();
        }
        else {
            final IntBuffer buffer = this.byteBuffer.asIntBuffer();
            buffer.rewind();
            buffer.position(index);
            jobject = buffer.get();
        }

        return JNI.toJObject(jobject);
    }

    @Override
    public void write(@Nonnull final JObject val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final JObject val) {
        final long pointerSize = sizeof((Pointer) null);
        if (pointerSize == 8) {
            //64-bit
            final LongBuffer buffer = this.byteBuffer.asLongBuffer();
            buffer.clear();
            buffer.position(index);
            buffer.put(val.address);
        }
        else if (pointerSize == 4) {
            //32-bit
            final IntBuffer buffer = this.byteBuffer.asIntBuffer();
            buffer.clear();
            buffer.position(index);
            buffer.put((int) val.address);
        }
    }
}
