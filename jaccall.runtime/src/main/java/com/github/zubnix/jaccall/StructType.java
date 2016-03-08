package com.github.zubnix.jaccall;


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
    protected final byte readByte(@Nonnegative final int offset) {
        return JNI.readByte(address() + offset,
                            0);
    }

    protected final void writeByte(@Nonnegative final int offset,
                                   final byte value) {
        JNI.writeByte(address() + offset,
                      0,
                      value);
    }

    //Short
    protected final short readShort(@Nonnegative final int offset) {
        return JNI.readShort(address() + offset,
                             0);
    }

    protected final void writeShort(@Nonnegative final int offset,
                                    final short value) {
        JNI.writeShort(address() + offset,
                       0,
                       value);
    }

    //Integer
    protected final int readInteger(@Nonnegative final int offset) {
        return JNI.readInt(address() + offset,
                           0);
    }

    protected final void writeInteger(@Nonnegative final int offset,
                                      final int value) {
        JNI.writeInt(address() + offset,
                     0,
                     value);
    }

    //c long
    @Nonnull
    protected final CLong readCLong(@Nonnegative final int offset) {
        return new CLong(JNI.readCLong(address() + offset,
                                       0));
    }

    protected final void writeCLong(@Nonnegative final int offset,
                                    @Nonnull final CLong value) {
        JNI.writeCLong(address() + offset,
                       0,
                       value.longValue());
    }

    //long long
    protected final long readLong(@Nonnegative final int offset) {
        return JNI.readLong(address() + offset,
                            0);
    }

    protected final void writeLong(@Nonnegative final int offset,
                                   final long value) {
        JNI.writeLong(address() + offset,
                      0,
                      value);
    }

    //float
    protected final float readFloat(@Nonnegative final int offset) {
        return JNI.readFloat(address() + offset,
                             0);
    }

    protected final void writeFloat(@Nonnegative final int offset,
                                    final float value) {
        JNI.writeFloat(address() + offset,
                       0,
                       value);
    }

    //double
    protected final double readDouble(@Nonnegative final int offset) {
        return JNI.readDouble(address() + offset,
                              0);
    }

    protected final void writeDouble(@Nonnegative final int offset,
                                     final double value) {
        JNI.writeDouble(address() + offset,
                        0,
                        value);
    }

    //pointer
    @Nonnull
    protected final <T> Pointer<T> readPointer(@Nonnegative final int offset,
                                               @Nonnull final Class<T> type) {
        return Pointer.wrap(type,
                            JNI.readPointer(address() + offset,
                                            0));
    }

    protected final void writePointer(@Nonnegative final int offset,
                                      @Nonnull final Pointer<?> pointer) {
        JNI.writePointer(address() + offset,
                         0,
                         pointer.address);
    }

    //struct type
    @Nonnull
    protected final <T extends StructType> T readStructType(@Nonnegative final int offset,
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

    protected final void writeStructType(@Nonnegative final int offset,
                                         @Nonnull final StructType structType) {
        JNI.writeStruct(address() + offset,
                        structType.address(),
                        structType.size);
    }

    //array
    @Nonnull
    protected final <T> Pointer<T> readArray(@Nonnegative final int offset,
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
