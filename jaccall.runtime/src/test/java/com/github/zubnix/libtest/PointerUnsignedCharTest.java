package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerUnsignedCharTest extends PointerFunc<PointerUnsignedCharTest> implements Testing.UnsignedCharTest {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_UINT8,
                                                      JNI.FFI_TYPE_UINT8);

    PointerUnsignedCharTest(final long address) {
        super(PointerUnsignedCharTest.class,
              address);
    }

    @Nonnull
    public static PointerUnsignedCharTest wrapFunc(final long address) {
        return new UnsignedCharTest_Jaccall_C(address);
    }

    @Nonnull
    public static PointerUnsignedCharTest nref(@Nonnull final Testing.UnsignedCharTest function) {
        if (function instanceof UnsignedCharTest_Jaccall_J) {
            return (PointerUnsignedCharTest) function;
        }
        return new UnsignedCharTest_Jaccall_J(function);
    }
}