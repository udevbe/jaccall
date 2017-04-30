package org.freedesktop.jaccall;


import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class StructType {

    final int size;
    private long    address  = 0L;
    private boolean autoFree = false;

    protected StructType(@Nonnegative final int size) {
        this.size = size;
    }

    final long address() {
        if (this.address == 0L) {
            this.autoFree = true;
            address(JNI.malloc(this.size));
        }
        return this.address;
    }

    final void address(final long address) {
        this.address = address;
    }

    //Byte
    protected final byte getByte(@Nonnegative final int offset) {
        return JNI.getByte(address() + offset,
                           0);
    }

    protected final void setByte(@Nonnegative final int offset,
                                 final byte value) {
        JNI.setByte(address() + offset,
                    0,
                    value);
    }

    //Short
    protected final short getShort(@Nonnegative final int offset) {
        return JNI.getShort(address() + offset,
                            0);
    }

    protected final void setShort(@Nonnegative final int offset,
                                  final short value) {
        JNI.setShort(address() + offset,
                     0,
                     value);
    }

    //Integer
    protected final int getInteger(@Nonnegative final int offset) {
        return JNI.getInt(address() + offset,
                          0);
    }

    protected final void setInteger(@Nonnegative final int offset,
                                    final int value) {
        JNI.setInt(address() + offset,
                   0,
                   value);
    }

    //c long
    @Nonnull
    protected final CLong getCLong(@Nonnegative final int offset) {
        return new CLong(JNI.getCLong(address() + offset,
                                      0));
    }

    protected final void setCLong(@Nonnegative final int offset,
                                  @Nonnull final CLong value) {
        JNI.setCLong(address() + offset,
                     0,
                     value.longValue());
    }

    //long long
    protected final long getLong(@Nonnegative final int offset) {
        return JNI.getLong(address() + offset,
                           0);
    }

    protected final void setLong(@Nonnegative final int offset,
                                 final long value) {
        JNI.setLong(address() + offset,
                    0,
                    value);
    }

    //float
    protected final float getFloat(@Nonnegative final int offset) {
        return JNI.getFloat(address() + offset,
                            0);
    }

    protected final void setFloat(@Nonnegative final int offset,
                                  final float value) {
        JNI.setFloat(address() + offset,
                     0,
                     value);
    }

    //double
    protected final double getDouble(@Nonnegative final int offset) {
        return JNI.getDouble(address() + offset,
                             0);
    }

    protected final void setDouble(@Nonnegative final int offset,
                                   final double value) {
        JNI.setDouble(address() + offset,
                      0,
                      value);
    }

    //pointer
    @Nonnull
    protected final <T> Pointer<T> getPointer(@Nonnegative final int offset,
                                              @Nonnull final Class<T> type) {
        return Pointer.wrap(type,
                            JNI.getPointer(address() + offset,
                                           0));
    }

    protected final void setPointer(@Nonnegative final int offset,
                                    @Nonnull final Pointer<?> pointer) {
        JNI.setPointer(address() + offset,
                       0,
                       pointer.address);
    }

    //struct type
    @Nonnull
    protected final <T extends StructType> T getStructType(@Nonnegative final int offset,
                                                           @Nonnull final Class<T> structTypeClass) {
        try {
            final T structType = structTypeClass.newInstance();
            structType.address(address() + offset);
            return structType;
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected final void setStructType(@Nonnegative final int offset,
                                       @Nonnull final StructType structType) {
        JNI.setStruct(address() + offset,
                      structType.address(),
                      structType.size);
    }

    //array
    @Nonnull
    protected final <T> Pointer<T> getArray(@Nonnegative final int offset,
                                            @Nonnull final Class<T> arrayType) {
        return Pointer.wrap(arrayType,
                            address() + offset);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (this.autoFree) {
            if (Pointer.ENABLE_LOG) {
                Logger.getLogger("jaccall")
                      .log(Level.FINE,
                           "Call to free for StructType of type=" + getClass() + " with address=0x" + String.format("%016X",
                                                                                                                    this.address));
            }
            JNI.free(this.address);
        }
    }
}
