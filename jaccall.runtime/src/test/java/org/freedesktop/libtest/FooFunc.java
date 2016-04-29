package org.freedesktop.libtest;

import org.freedesktop.jaccall.ByVal;
import org.freedesktop.jaccall.Functor;
import org.freedesktop.jaccall.Ptr;
import org.freedesktop.jaccall.Unsigned;

@Functor
public interface FooFunc {

    byte $(@Ptr long arg0,
           @Unsigned int arg1,
           @ByVal(TestStruct.class) long arg2);
}
