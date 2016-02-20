package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnsignedLongLongFunc_Jaccall_C extends PointerUnsignedLongLongFunc {

    static {
        JNI.linkFuncPtr(UnsignedLongLongFunc_Jaccall_C.class,
                        "_$",
                        2,
                        "(JJ)J",
                        FFI_CIF);
    }

    UnsignedLongLongFunc_Jaccall_C(final long address,
                                   final ByteBuffer buffer) {
        super(address,
              buffer);
    }

    @Override
    public long $(final long value) {
        return _$(this.address,
                  value);
    }

    private static native long _$(final long address,
                                  final long value);
}
