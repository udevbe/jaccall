package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerIntTest extends PointerFunc<PointerIntTest> implements Testing.IntTest {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT32,
                                                      JNI.FFI_TYPE_SINT32);

    PointerIntTest(final long address) {
        super(PointerIntTest.class,
              address);
    }

    @Nonnull
    public static PointerIntTest wrapFunc(final long address) {
        return new IntTest_Jaccall_C(address);
    }

    @Nonnull
    public static PointerIntTest nref(@Nonnull final Testing.IntTest function) {
        if (function instanceof PointerIntTest) {
            return (PointerIntTest) function;
        }
        return new IntTest_Jaccall_J(function);
    }
}
