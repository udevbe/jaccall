package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerUnsignedShortTest extends PointerFunc<PointerUnsignedShortTest> implements Testing.UnsignedShortTest {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_UINT16,
                                                      JNI.FFI_TYPE_UINT16);

    PointerUnsignedShortTest(final long address) {
        super(PointerUnsignedShortTest.class,
              address);
    }

    @Nonnull
    public static PointerUnsignedShortTest wrapFunc(final long address) {
        return new UnsignedShortTest_Jaccall_C(address);
    }

    @Nonnull
    public static PointerUnsignedShortTest nref(@Nonnull final Testing.UnsignedShortTest function) {
        if (function instanceof UnsignedShortTest_Jaccall_J) {
            return (PointerUnsignedShortTest) function;
        }
        return new UnsignedShortTest_Jaccall_J(function);
    }
}
