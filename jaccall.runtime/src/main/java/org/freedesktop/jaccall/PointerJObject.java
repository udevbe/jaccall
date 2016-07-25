package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.freedesktop.jaccall.Size.sizeof;

final class PointerJObject extends Pointer<Object> {
    public PointerJObject(final long address,
                          final boolean autoFree) {
        super(Object.class,
              address,
              autoFree,
              Size.sizeof((Pointer) null));
    }

    @Nonnull
    @Override
    public Object dref() {
        return JNI.toObject(this.address);
    }

    @Nonnull
    @Override
    public Object dref(@Nonnegative final int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(@Nonnull final Object val) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Object val) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        if (ENABLE_LOG) {
            Logger.getLogger("jaccall")
                  .log(Level.FINE,
                       "Explicit call to free for Pointer POJO of type=" + this.type + " with address=0x" + String.format("%016X",
                                                                                                                          this.address));
        }
        JNI.DeleteGlobalRef(this.address);
    }
}