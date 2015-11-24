package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerCharTest extends PointerFunc<Testing.CharTest> implements Testing.CharTest {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT8,
                                                      JNI.FFI_TYPE_SINT8);

    PointerCharTest(final long address) {
        super(Testing.CharTest.class,
              address);
    }

    @Nonnull
    public static PointerCharTest wrapFunc(final long address) {
        return new CharTest_Jaccall_C(address);
    }

    @Nonnull
    public static PointerCharTest nref(@Nonnull final Testing.CharTest function) {
        if (function instanceof CharTest_Jaccall_J) {
            return (PointerCharTest) function;
        }
        return new CharTest_Jaccall_J(function);
    }


}
