package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerDoubleTest extends PointerFunc<PointerDoubleTest> implements Testing.DoubleTest {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_DOUBLE,
                                                      JNI.FFI_TYPE_DOUBLE);

    PointerDoubleTest(final long address) {
        super(PointerDoubleTest.class,
              address);
    }

    @Nonnull
    public static PointerDoubleTest wrapFunc(final long address) {
        return new DoubleTest_Jaccall_C(address);
    }

    @Nonnull
    public static PointerDoubleTest nref(@Nonnull final Testing.DoubleTest function) {
        if (function instanceof PointerDoubleTest) {
            return (PointerDoubleTest) function;
        }
        return new DoubleTest_Jaccall_J(function);
    }
}
