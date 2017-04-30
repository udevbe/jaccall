package org.freedesktop.libtest;

import org.freedesktop.jaccall.JNI;

import javax.annotation.Generated;

@Generated("org.freedesktop.jaccall.compiletime.functor.FunctionPointerGenerator")
final class FooFunc_Jaccall_C extends PointerFooFunc {

    static {
        JNI.linkFuncPtr(FooFunc_Jaccall_C.class,
                        "_invoke",
                        4,
                        "(JJIJ)B",
                        FFI_CIF);
    }

    FooFunc_Jaccall_C(final long address) {
        super(address);
    }

    @Override
    public byte invoke(final long arg0,
                  final int arg1,
                  final long arg2) {
        return _invoke(this.address,
                  arg0,
                  arg1,
                  arg2);
    }

    private static native byte _invoke(long address,
                                  long arg0,
                                  int arg1,
                                  long arg2);
}
