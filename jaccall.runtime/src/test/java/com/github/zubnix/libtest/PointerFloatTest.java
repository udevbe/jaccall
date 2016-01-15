package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerFloatTest extends PointerFunc<PointerFloatTest> implements Testing.FloatTest {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_FLOAT,
                                                      JNI.FFI_TYPE_FLOAT);

    PointerFloatTest(final long address) {
        super(PointerFloatTest.class,
              address);
    }

    @Nonnull
    public static PointerFloatTest wrapFunc(final long address) {
        return new FloatTest_Jaccall_C(address);
    }

    @Nonnull
    public static PointerFloatTest nref(@Nonnull final Testing.FloatTest function) {
        if (function instanceof PointerFloatTest) {
            return (PointerFloatTest) function;
        }
        return new FloatTest_Jaccall_J(function);
    }
}
