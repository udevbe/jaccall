package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerLongTest extends PointerFunc<PointerLongTest> implements Testing.LongTest {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SLONG,
                                                      JNI.FFI_TYPE_SLONG);

    PointerLongTest(final long address) {
        super(PointerLongTest.class,
              address);
    }

    @Nonnull
    public static PointerLongTest wrapFunc(final long address) {
        return new LongTest_Jaccall_C(address);
    }

    @Nonnull
    public static PointerLongTest nref(@Nonnull final Testing.LongTest function) {
        if (function instanceof PointerLongTest) {
            return (PointerLongTest) function;
        }
        return new LongTest_Jaccall_J(function);
    }
}
