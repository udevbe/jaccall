package org.freedesktop.libtest;

import org.freedesktop.jaccall.ByVal;
import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Ptr;
import org.freedesktop.jaccall.Unsigned;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class FooFunc_Jaccall_J extends PointerFooFunc {

    private static final long JNI_METHOD_ID = JNI.GetMethodID(FooFunc.class,
                                                              "$",
                                                              "(JIJ)B");

    @Nonnull
    private final FooFunc fooFunction;

    FooFunc_Jaccall_J(@Nonnull final FooFunc fooFunction) {
        super(JNI.ffi_closure(FFI_CIF,
                              fooFunction,
                              JNI_METHOD_ID));
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
}
