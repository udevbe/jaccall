package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.funcptr.FunctionPointerGenerator")
public abstract class PointerTestFunc extends PointerFunc<PointerTestFunc> implements TestFunc {

    static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT8,
                                                      JNI.FFI_TYPE_POINTER,
                                                      JNI.FFI_TYPE_UINT32,
                                                      TestStruct.FFI_TYPE);

    PointerTestFunc(final long address) {
        super(PointerTestFunc.class,
              address);
    }

    public static PointerTestFunc wrapFunc(final long address) {
        return new TestFunc_Jaccall_C(address);
    }

    public static PointerTestFunc nref(final TestFunc function) {
        return new TestFunc_Jaccall_J(function);
    }
}
