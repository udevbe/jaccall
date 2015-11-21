package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.LinkSymbols;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.LinkerGenerator")
public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {
    public Testing_Jaccall_LinkSymbols() {
        super(new String[]{"doStaticTest",
                           "doStaticTest2",
                           "doStaticUnionTest",
                           "doStaticUnionTest2"},
              new byte[]{5,
                         5,
                         3,
                         2},
              new long[]{JNI.ffi_callInterface(TestStruct.FFI_TYPE,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_SINT8,
                                               JNI.FFI_TYPE_UINT16,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER,
                                               TestStruct.FFI_TYPE,
                                               JNI.FFI_TYPE_SINT8,
                                               JNI.FFI_TYPE_UINT16,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(TestUnion.FFI_TYPE,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_SINT32,
                                               JNI.FFI_TYPE_FLOAT),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER,
                                               TestUnion.FFI_TYPE,
                                               JNI.FFI_TYPE_SINT32)},
              new String[]{"(JBSJJ)J",
                           "(JBSJJ)J",
                           "(JIF)J",
                           "(JI)J"
              });
    }
}
