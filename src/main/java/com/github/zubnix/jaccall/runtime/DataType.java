package com.github.zubnix.jaccall.runtime;


import com.github.zubnix.jaccall.runtime.api.Pointer;

public abstract class DataType<T> {

    protected abstract Pointer<T> getAddress();
}
