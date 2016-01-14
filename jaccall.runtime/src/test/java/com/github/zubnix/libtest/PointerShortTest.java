package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
abstract class PointerShortTest extends PointerFunc<PointerShortTest> implements Testing.ShortTest {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT16,
                                                      JNI.FFI_TYPE_SINT16);

    PointerShortTest(final long address) {
        super(PointerShortTest.class,
              address);
    }

    @Nonnull
    public static PointerShortTest wrapFunc(final long address) {
        return new ShortTest_Jaccall_C(address);
    }

    @Nonnull
    public static PointerShortTest nref(@Nonnull final Testing.ShortTest function) {
        if (function instanceof ShortTest_Jaccall_J) {
            return (PointerShortTest) function;
        }
        return new ShortTest_Jaccall_J(function);
    }
}
