package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Ptr;
import com.github.zubnix.jaccall.Unsigned;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
final class TestFunc_Jaccall_J extends PointerTestFunc {

    @Nonnull
    private final TestFunc testFunction;

    TestFunc_Jaccall_J(@Nonnull final TestFunc testFunction) {
        super(JNI.ffi_closure(PointerTestFunc.FFI_CIF,
                              testFunction));
        this.testFunction = testFunction;
    }

    @Override
    public byte $(@Ptr final long arg0,
                  @Unsigned final int arg1,
                  @ByVal(TestStruct.class) final long arg2) {
        return this.testFunction.$(arg0,
                                   arg1,
                                   arg2);
    }

    @Override
    public void close() {
        JNI.ffi_closure_free(this.address);
        JNI.DeleteGlobalRef(this.testFunction);
    }
}
