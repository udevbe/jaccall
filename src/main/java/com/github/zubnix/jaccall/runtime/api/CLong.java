package com.github.zubnix.jaccall.runtime.api;


public final class CLong extends Number {

    private long value;

    public CLong(final long value) {
        this.value = value;
    }


    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }
}
