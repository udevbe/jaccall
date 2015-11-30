package com.github.zubnix.jaccall.runtime;


import java.nio.ByteBuffer;

public abstract class StructType {

    private ByteBuffer buffer;

    protected ByteBuffer buffer() {
        if (this.buffer == null) {
            buffer(ByteBuffer.allocate((int) size()));
        }
        return buffer;
    }

    void buffer(final ByteBuffer buffer) {
        buffer.rewind();
        buffer.clear();
        this.buffer = buffer;
    }

    protected abstract long size();
}
