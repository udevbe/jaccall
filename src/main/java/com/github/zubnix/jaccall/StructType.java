package com.github.zubnix.jaccall;


import java.nio.ByteBuffer;

import static java.nio.ByteBuffer.allocate;

public abstract class StructType {


    protected static int newOffset(int align,
                                   int offset) {
        return (offset + align - 1) & ~(align - 1);
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
