package com.github.zubnix.jaccall.runtime;


import com.github.zubnix.jaccall.runtime.api.Pointer;

public interface StructType<T> {

    Pointer<T> ref();
}
