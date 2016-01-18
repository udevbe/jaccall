package com.github.zubnix.libtest;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.LinkSymbols;

import javax.annotation.Generated;

@Generated("com.github.zubnix.jaccall.compiletime.LinkerGenerator")
public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {
    public Testing_Jaccall_LinkSymbols() {
        super(new String[]{"charTest",
                           "unsignedCharTest",
                           "shortTest",
                           "unsignedShortTest",
                           "intTest",
                           "unsignedIntTest",
                           "longTest",
                           "unsignedLongTest",
                           "longLongTest",
                           "unsignedLongLongTest",
                           "floatTest",
                           "doubleTest",
                           "pointerTest",
                           "structTest",
                           "structTest2",
                           "unionTest",
                           "unionTest2",
                           "noArgsTest",
                           "noArgsFuncPtrTest",
                           "getFunctionPointerTest",
                           "functionPointerTest",
                           "charTestFunctionPointer",
                           "unsignedCharTestFunctionPointer",
                           "shortTestFunctionPointer",
                           "unsignedShortTestFunctionPointer",
                           "intTestFunctionPointer",
                           "unsignedIntTestFunctionPointer",
                           "longTestFunctionPointer",
                           "unsignedLongTestFunctionPointer",
                           "longLongTestFunctionPointer",
                           "unsignedLongLongTestFunctionPointer",
                           "floatTestFunctionPointer",
                           "doubleTestFunctionPointer",
                           "pointerTestFunctionPointer",
                           "structTestFunctionPointer"
              },
              new byte[]{1,
                         1,
                         1,
                         1,
                         1,
                         1,
                         1,
                         1,
                         1,
                         1,
                         1,
                         1,
                         1,
                         7,
                         7,
                         3,
                         2,
                         0,
                         0,
                         0,
                         4,
                         0,
                         0,
                         0,
                         0,
                         0,
                         0,
                         0,
                         0,
                         0,
                         0,
                         0,
                         0,
                         0,
                         0
              },
              new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_SINT8,
                                               JNI.FFI_TYPE_SINT8),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_UINT8,
                                               JNI.FFI_TYPE_UINT8),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_SINT16,
                                               JNI.FFI_TYPE_SINT16),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_UINT16,
                                               JNI.FFI_TYPE_UINT16),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_SINT32,
                                               JNI.FFI_TYPE_SINT32),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_UINT32,
                                               JNI.FFI_TYPE_UINT32),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_SLONG,
                                               JNI.FFI_TYPE_SLONG),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_ULONG,
                                               JNI.FFI_TYPE_ULONG),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_SINT64,
                                               JNI.FFI_TYPE_SINT64),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_UINT64,
                                               JNI.FFI_TYPE_UINT64),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_FLOAT,
                                               JNI.FFI_TYPE_FLOAT),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_DOUBLE,
                                               JNI.FFI_TYPE_DOUBLE),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(TestStruct.FFI_TYPE,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_SINT8,
                                               JNI.FFI_TYPE_UINT16,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_SINT64,
                                               JNI.FFI_TYPE_FLOAT),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER,
                                               TestStruct.FFI_TYPE,
                                               JNI.FFI_TYPE_SINT8,
                                               JNI.FFI_TYPE_UINT16,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_SINT64,
                                               JNI.FFI_TYPE_FLOAT),
                         JNI.ffi_callInterface(TestUnion.FFI_TYPE,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_SINT32,
                                               JNI.FFI_TYPE_FLOAT),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER,
                                               TestUnion.FFI_TYPE,
                                               JNI.FFI_TYPE_SINT32),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_VOID),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_SINT8,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_UINT32,
                                               TestStruct.FFI_TYPE),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER),
                         JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER)
              },
              new String[]{"(B)B",
                           "(B)B",
                           "(S)S",
                           "(S)S",
                           "(I)I",
                           "(I)I",
                           "(J)J",
                           "(J)J",
                           "(J)J",
                           "(J)J",
                           "(F)F",
                           "(D)D",
                           "(J)J",
                           "(JBSJJJF)J",
                           "(JBSJJJF)J",
                           "(JIF)J",
                           "(JI)J",
                           "()V",
                           "()J",
                           "()J",
                           "(JJIJ)B",
                           "()J",
                           "()J",
                           "()J",
                           "()J",
                           "()J",
                           "()J",
                           "()J",
                           "()J",
                           "()J",
                           "()J",
                           "()J",
                           "()J",
                           "()J",
                           "()J"
              });
    }
}
