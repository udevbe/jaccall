package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerUnsignedLongLongTest extends PointerFunc<PointerUnsignedLongLongTest> implements Testing.UnsignedLongLongTest {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_UINT64,
                                                      JNI.FFI_TYPE_UINT64);

    PointerUnsignedLongLongTest(final long address) {
        super(PointerUnsignedLongLongTest.class,
              address);
    }

    @Nonnull
    public static PointerUnsignedLongLongTest wrapFunc(final long address) {
        return new UnsignedLongLongTest_Jaccall_C(address);
    }

    @Nonnull
    public static PointerUnsignedLongLongTest nref(@Nonnull final Testing.UnsignedLongLongTest function) {
        if (function instanceof PointerUnsignedLongLongTest) {
            return (PointerUnsignedLongLongTest) function;
        }
        return new UnsignedLongLongTest_Jaccall_J(function);
    }
}
