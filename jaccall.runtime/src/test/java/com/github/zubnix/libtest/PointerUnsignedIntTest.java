package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerUnsignedIntTest extends PointerFunc<PointerUnsignedIntTest> implements Testing.UnsignedIntTest {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_UINT32,
                                                      JNI.FFI_TYPE_UINT32);

    PointerUnsignedIntTest(final long address) {
        super(PointerUnsignedIntTest.class,
              address);
    }

    @Nonnull
    public static PointerUnsignedIntTest wrapFunc(final long address) {
        return new UnsignedIntTest_Jaccall_C(address);
    }

    @Nonnull
    public static PointerUnsignedIntTest nref(@Nonnull final Testing.UnsignedIntTest function) {
        if (function instanceof PointerUnsignedIntTest) {
            return (PointerUnsignedIntTest) function;
        }
        return new UnsignedIntTest_Jaccall_J(function);
    }
}
