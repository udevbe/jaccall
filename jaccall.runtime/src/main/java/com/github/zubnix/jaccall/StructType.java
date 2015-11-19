package com.github.zubnix.jaccall;


import java.nio.ByteBuffer;

import static java.nio.ByteBuffer.allocate;

public abstract class StructType {

    protected static int MAX(int... values) {
        int max = 0;
        for (int value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    protected static int newOffset(final int align,
                                   final int offset) {
        return (offset + align - 1) & ~(align - 1);
    }

    protected static long address(final ByteBuffer buffer,
                                  final int offset) {
        final long pointerSize = Size.sizeof((Pointer) null);
        final long address;

        if (pointerSize == 8) {
            address = buffer.getLong(offset);
        }
        else {
            address = buffer.getInt(offset);
        }

        return address;
    }

    protected static void address(final ByteBuffer buffer,
                                  final int offset,
                                  final Pointer<?> pointer) {
        final long pointerSize = Size.sizeof((Pointer) null);

        if (pointerSize == 8) {
            buffer.putLong(offset,
                           pointer.address);
        }
        else {
            buffer.putInt(offset,
                          (int) pointer.address);
        }
    }

    final int size;

    private ByteBuffer buffer;

    protected StructType(final int size) {
        this.size = size;
    }

    protected ByteBuffer buffer() {
        if (this.buffer == null) {
            buffer(allocate(this.size));
        }
        return this.buffer;
    }

    void buffer(final ByteBuffer buffer) {
        buffer.rewind();
        buffer.clear();
        this.buffer = buffer;
    }
}
