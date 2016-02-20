package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class DoubleFunc_Jaccall_C extends PointerDoubleFunc {

    static {
        JNI.linkFuncPtr(DoubleFunc_Jaccall_C.class,
                        "_$",
                        2,
                        "(JD)D",
                        FFI_CIF);
    }

    DoubleFunc_Jaccall_C(final long address,
                         final ByteBuffer buffer) {
        super(address,
              buffer);
    }

    @Override
    public double $(final double value) {
        return _$(this.address,
                  value);
    }

    private static native double _$(final long address,
                                    final double value);
}
