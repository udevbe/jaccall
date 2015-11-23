package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;
import com.github.zubnix.jaccall.Ptr;
import com.github.zubnix.jaccall.Unsigned;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;


final class TestFunction_Jaccall_PointerFunc extends PointerFunc<TestFunction> implements TestFunction {

    private static final byte   NRO_ARGS = 3;
    private static final String JNI_SIG  = "(JIJ)B";
    private static final long   FFI_CIF  = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT8,
                                                                 JNI.FFI_TYPE_POINTER,
                                                                 JNI.FFI_TYPE_UINT32,
                                                                 TestStruct.FFI_TYPE);

    static {
        JNI.link(TestFunction_Jaccall_PointerFunc.class,
                 "_$",
                 NRO_ARGS,
                 JNI_SIG,
                 FFI_CIF);
    }

    TestFunction_Jaccall_PointerFunc(@Nonnull final Type type,
                                     final long address,
                                     @Nonnull final ByteBuffer byteBuffer) {
        super(type,
              address,
              byteBuffer);
    }

    @Override
    public byte $(@Ptr long arg0,
                  @Unsigned int arg1,
                  @ByVal(TestStruct.class) long arg2) {
        return _$(this.address,
                  arg0,
                  arg1,
                  arg2);
    }

    private static native byte _$(long address,
                                  @Ptr long arg0,
                                  @Unsigned int arg1,
                                  @ByVal(TestStruct.class) long arg2);


}
