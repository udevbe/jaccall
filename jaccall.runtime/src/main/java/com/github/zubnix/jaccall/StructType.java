package com.github.zubnix.jaccall;


import java.nio.ByteBuffer;

import static java.nio.ByteBuffer.allocate;

public abstract class StructType {

    protected static int newOffset(final int align,
                                   final int offset) {
        return (offset + align - 1) & ~(align - 1);
    }

    final int size;

    private ByteBuffer buffer;

    protected StructType(final int size) {
        this.size = size;
    }

    final ByteBuffer buffer() {
        if (this.buffer == null) {
            buffer(allocate(this.size));
        }
        return this.buffer;
    }

    final void buffer(final ByteBuffer buffer) {
        buffer.rewind();
        buffer.clear();
        this.buffer = buffer;
    }

    //Byte
    protected final byte readByte(final int offset) {
        return buffer().get(offset);
    }

    protected final void writeByte(final int offset,
                                   final byte value) {
        buffer().put(offset,
                     value);
    }

    //Short
    protected final short readShort(final int offset) {
        return buffer().getShort(offset);
    }

    protected final void writeShort(final int offset,
                                    final short value) {
        buffer().putShort(offset,
                          value);
    }

    //Integer
    protected final int readInteger(final int offset) {
        return buffer().getInt(offset);
    }

    protected final void writeInteger(final int offset,
                                      final int value) {
        buffer().putInt(offset,
                        value);
    }

    //c long
    protected final CLong readCLong(final int offset) {
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

    protected final void writeCLong(final int offset,
                                    final CLong value) {
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
    protected final long readLong(final int offset) {
        return buffer().getLong(offset);
    }

    protected final void writeLong(final int offset,
                                   final long value) {
        buffer().putLong(offset,
                         value);
    }

    //float
    protected final float readFloat(final int offset) {
        return buffer().getFloat(offset);
    }

    protected final void writeFloat(final int offset,
                                    final float value) {
        buffer().putFloat(offset,
                          value);
    }

    //double
    protected final double readDouble(final int offset) {
        return buffer().getDouble(offset);
    }

    protected final void writeDouble(final int offset,
                                     final double value) {
        buffer().putDouble(offset,
                           value);
    }

    //pointer
    protected final <T> Pointer<T> readPointer(final int offset,
                                               final Class<T> type) {
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

    protected final void writePointer(final int offset,
                                      final Pointer<?> pointer) {
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
    protected final <T extends StructType> T readStructType(final int offset,
                                                            final Class<T> structTypeClass) {
        return Pointer.wrap(structTypeClass,
                            buffer())
                      .offset(offset)
                      .dref();
    }

    protected final void writeStructType(final int offset,
                                         final StructType structType) {
        structType.buffer()
                  .rewind();
        final PointerStruct pointer = (PointerStruct) Pointer.wrap(structType.getClass(),
                                                                   buffer());
        pointer.offset(offset)
               .write(structType);
    }

    //array
    protected final <T> Pointer<T> readArray(final int offset,
                                             final Class<T> arrayType) {
        return Pointer.wrap(arrayType,
                            buffer())
                      .offset(offset);
    }
}
