package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.CFunction;
import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;

import java.nio.ByteBuffer;

abstract class TestFunction_Jaccall_CFunction extends CFunction {

    TestFunction_Jaccall_CFunction() {
        super(TestFunction.class,
              JNI.ffi_callInterface(123),
              ByteBuffer.allocate(0));
    }


    public int _(Pointer<?> arg0){

    }
}
