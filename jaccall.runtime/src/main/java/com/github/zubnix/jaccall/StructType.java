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

    //byte
    protected final byte readChar(int offset) {
        return buffer().get(offset);
    }

    protected final void writeChar(int offset,
                                   byte value) {
        buffer().put(offset,
                     value);
    }

    //short
    protected final short readShort(int offset) {
        return buffer().getShort(offset);
    }

    protected final void writeShort(int offset,
                                    short value) {
        buffer().putShort(offset,
                          value);
    }

    //int
    protected final int readInt(int offset) {
        return buffer().getInt(offset);
    }

    protected final void writeInt(int offset,
                                  int value) {
        buffer().putInt(offset,
                        value);
    }

    //c long
    protected final CLong readCLong(int offset) {
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

    protected final void writeCLong(int offset,
                                    CLong value) {
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
    protected final long readLong(int offset) {
        return buffer().getLong(offset);
    }

    protected final void writeLong(int offset,
                                   long value) {
        buffer().putLong(offset,
                         value);
    }

    //float
    protected final float readFloat(int offset) {
        return buffer().getFloat(offset);
    }

    protected final void writeFloat(int offset,
                                    float value) {
        buffer().putFloat(offset,
                          value);
    }

    //double
    protected final double readDouble(int offset) {
        return buffer().getDouble(offset);
    }

    protected final void writeDouble(int offset,
                                     double value) {
        buffer().putDouble(offset,
                           value);
    }

    //pointer
    protected final <T> Pointer<T> readPointer(final int offset,
                                               Class<T> type) {
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
    protected final <T extends StructType> T readStructType(int offset,
                                                            Class<T> structTypeClass) {
        return Pointer.wrap(structTypeClass,
                            buffer())
                      .offset(offset)
                      .dref();
    }

    protected final void writeStructType(int offset,
                                         StructType structType) {
        structType.buffer()
                  .rewind();
        final PointerStruct pointer = (PointerStruct) Pointer.wrap(structType.getClass(),
                                                                   buffer());
        pointer.offset(offset)
               .write(structType);
    }

    //array
    protected final <T> Pointer<T> readArray(int offset,
                                             Class<T> arrayType) {
        return Pointer.wrap(arrayType,
                            buffer())
                      .offset(offset);
    }
}
