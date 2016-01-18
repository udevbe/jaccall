package com.github.zubnix.jaccall;


import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static java.nio.ByteBuffer.allocateDirect;

//TODO unit test all read+write struct operations
public abstract class StructType {

    protected static int newOffset(final int align,
                                   final int offset) {
        return (offset + align - 1) & ~(align - 1);
    }

    final int size;

    private ByteBuffer buffer;

    protected StructType(@Nonnegative final int size) {
        this.size = size;
    }

    @Nonnull
    final ByteBuffer buffer() {
        if (this.buffer == null) {
            //TODO allocate an indirect bytebuffer once the CArray type has been added
            buffer(allocateDirect(this.size));
        }
        return this.buffer;
    }

    final void buffer(@Nonnull final ByteBuffer buffer) {
        buffer.rewind();
        buffer.clear();
        buffer.order(ByteOrder.nativeOrder());
        this.buffer = buffer;
    }

    //Byte
    protected final byte readByte(@Nonnegative final int offset) {
        return buffer().get(offset);
    }

    protected final void writeByte(@Nonnegative final int offset,
                                   final byte value) {
        buffer().put(offset,
                     value);
    }

    //Short
    protected final short readShort(@Nonnegative final int offset) {
        return buffer().getShort(offset);
    }

    protected final void writeShort(@Nonnegative final int offset,
                                    final short value) {
        buffer().putShort(offset,
                          value);
    }

    //Integer
    protected final int readInteger(@Nonnegative final int offset) {
        return buffer().getInt(offset);
    }

    protected final void writeInteger(@Nonnegative final int offset,
                                      final int value) {
        buffer().putInt(offset,
                        value);
    }

    //c long
    @Nonnull
    protected final CLong readCLong(@Nonnegative final int offset) {
        final long size = Size.sizeof((CLong) null);
        final long value;

        if (size == 8) {
            value = buffer().getLong(offset);
        }
        else {
            value = buffer().getInt(offset);
        }

        return new CLong(value);
    }

    protected final void writeCLong(@Nonnegative final int offset,
                                    @Nonnull final CLong value) {
        final long size = Size.sizeof((CLong) null);

        if (size == 8) {
            buffer().putLong(offset,
                             value.longValue());
        }
        else {
            buffer().putInt(offset,
                            value.intValue());
        }
    }

    //long long
    protected final long readLong(@Nonnegative final int offset) {
        return buffer().getLong(offset);
    }

    protected final void writeLong(@Nonnegative final int offset,
                                   final long value) {
        buffer().putLong(offset,
                         value);
    }

    //float
    protected final float readFloat(@Nonnegative final int offset) {
        return buffer().getFloat(offset);
    }

    protected final void writeFloat(@Nonnegative final int offset,
                                    final float value) {
        buffer().putFloat(offset,
                          value);
    }

    //double
    protected final double readDouble(@Nonnegative final int offset) {
        return buffer().getDouble(offset);
    }

    protected final void writeDouble(@Nonnegative final int offset,
                                     final double value) {
        buffer().putDouble(offset,
                           value);
    }

    //pointer
    @Nonnull
    protected final <T> Pointer<T> readPointer(@Nonnegative final int offset,
                                               @Nonnull final Class<T> type) {
        final long size = Size.sizeof((Pointer) null);
        final long address;

        if (size == 8) {
            address = buffer().getLong(offset);
        }
        else {
            address = buffer().getInt(offset);
        }

        return Pointer.wrap(address)
                      .castp(type);
    }

    protected final void writePointer(@Nonnegative final int offset,
                                      @Nonnull final Pointer<?> pointer) {
        final long size = Size.sizeof((Pointer) null);

        if (size == 8) {
            buffer().putLong(offset,
                             pointer.address);
        }
        else {
            buffer().putInt(offset,
                            (int) pointer.address);
        }
    }

    //struct type
    @Nonnull
    protected final <T extends StructType> T readStructType(@Nonnegative final int offset,
                                                            @Nonnull final Class<T> structTypeClass) {
        buffer().position(offset);
        final ByteBuffer structTypeBuffer = buffer().slice();
        try {
            final T structType = structTypeClass.newInstance();
            structType.buffer(structTypeBuffer);
            return structType;
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected final void writeStructType(@Nonnegative final int offset,
                                         @Nonnull final StructType structType) {
        structType.buffer()
                  .rewind();
        buffer().position(offset);
        buffer().put(structType.buffer());
    }

    //array
    @Nonnull
    //TODO instead of a pointer, we should return a CArray object that can be backed by either a direct or indirect bytebuffer.
    protected final <T> Pointer<T> readArray(@Nonnegative final int offset,
                                             @Nonnull final Class<T> arrayType) {
        return Pointer.wrap(Byte.class,
                            buffer())
                      .offset(offset)
                      .castp(arrayType);
    }
}
