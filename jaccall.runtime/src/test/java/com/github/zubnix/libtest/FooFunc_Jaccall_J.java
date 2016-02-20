package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Ptr;
import com.github.zubnix.jaccall.Unsigned;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

@Generated("com.github.zubnix.jaccall.compiletime.functor.FunctionPointerGenerator")
final class FooFunc_Jaccall_J extends PointerFooFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(FooFunc.class,
                                                              "$",
                                                              "(JIJ)B");

    @Nonnull
    private final FooFunc fooFunction;

    FooFunc_Jaccall_J(@Nonnull final FooFunc fooFunction) {
        super(JNI.ffi_closure(FFI_CIF,
                              fooFunction,
                              JNI_METHOD_ID),
              ByteBuffer.allocate(0));
        this.fooFunction = fooFunction;
    }

    @Override
    public byte $(@Ptr final long arg0,
                  @Unsigned final int arg1,
                  @ByVal(TestStruct.class) final long arg2) {
        return this.fooFunction.$(arg0,
                                  arg1,
                                  arg2);
    }

    @Override
    public void close() {
        JNI.ffi_closure_free(this.address);
        JNI.DeleteGlobalRef(this.fooFunction);
    }
}
