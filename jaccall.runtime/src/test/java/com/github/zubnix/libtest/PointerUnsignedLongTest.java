package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerUnsignedLongTest extends PointerFunc<PointerUnsignedLongTest> implements Testing.LongTest {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_ULONG,
                                                      JNI.FFI_TYPE_ULONG);

    PointerUnsignedLongTest(final long address) {
        super(PointerUnsignedLongTest.class,
              address);
    }

    @Nonnull
    public static PointerUnsignedLongTest wrapFunc(final long address) {
        return new UnsignedLongTest_Jaccall_C(address);
    }

    @Nonnull
    public static PointerUnsignedLongTest nref(@Nonnull final Testing.UnsignedLongTest function) {
        if (function instanceof PointerUnsignedLongTest) {
            return (PointerUnsignedLongTest) function;
        }
        return new UnsignedLongTest_Jaccall_J(function);
    }
}
