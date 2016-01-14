package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerCharTest extends PointerFunc<PointerCharTest> implements Testing.CharTest {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT8,
                                                      JNI.FFI_TYPE_SINT8);

    PointerCharTest(final long address) {
        super(PointerCharTest.class,
              address);
    }

    @Nonnull
    public static PointerCharTest wrapFunc(final long address) {
        return new CharTest_Jaccall_C(address);
    }

    @Nonnull
    public static PointerCharTest nref(@Nonnull final Testing.CharTest function) {
        if (function instanceof PointerCharTest) {
            return (PointerCharTest) function;
        }
        return new CharTest_Jaccall_J(function);
    }


}
