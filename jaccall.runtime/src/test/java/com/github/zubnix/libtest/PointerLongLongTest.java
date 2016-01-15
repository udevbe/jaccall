package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerLongLongTest extends PointerFunc<PointerLongLongTest> implements Testing.LongLongTest {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT64,
                                                      JNI.FFI_TYPE_SINT64);

    PointerLongLongTest(final long address) {
        super(PointerLongLongTest.class,
              address);
    }

    @Nonnull
    public static PointerLongLongTest wrapFunc(final long address) {
        return new LongLongTest_Jaccall_C(address);
    }

    @Nonnull
    public static PointerLongLongTest nref(@Nonnull final Testing.LongLongTest function) {
        if (function instanceof PointerLongLongTest) {
            return (PointerLongLongTest) function;
        }
        return new LongLongTest_Jaccall_J(function);
    }
}
