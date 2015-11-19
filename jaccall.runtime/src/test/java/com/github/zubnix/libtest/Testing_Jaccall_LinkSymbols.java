package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.LinkSymbols;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.LinkerGenerator")
public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {
    public Testing_Jaccall_LinkSymbols() {
        super(new String[]{"doStaticTest"},
              new byte[]{5},
              new long[]{JNI.ffi_callInterface(TestStruct.FFI_TYPE,
                                               JNI.ffi_type_pointer(),
                                               JNI.ffi_type_sint8(),
                                               JNI.ffi_type_uint16(),
                                               JNI.ffi_type_pointer(),
                                               JNI.ffi_type_pointer())},
              new String[]{"(JBSJJ)J"});
    }
}
