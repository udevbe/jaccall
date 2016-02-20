package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class UnsignedCharFunc_Jaccall_C extends PointerUnsignedCharFunc {

    static {
        JNI.linkFuncPtr(UnsignedCharFunc_Jaccall_C.class,
                        "_$",
                        2,
                        "(JB)B",
                        FFI_CIF);
    }

    UnsignedCharFunc_Jaccall_C(final long address,
                               final ByteBuffer buffer) {
        super(address,
              buffer);
    }

    @Override
    public byte $(final byte value) {
        return _$(this.address,
                  value);
    }

    private static native byte _$(final long address,
                                  final byte value);
}
