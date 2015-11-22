package com.github.zubnix.libtest;

import com.github.zubnix.jaccall.CType;
import com.github.zubnix.jaccall.Func;
import com.github.zubnix.jaccall.Pointer;


@Func(ret = CType.INT,
      args = {
              CType.POINTER
      })
public class TestFunction extends TestFunction_Jaccall_CFunction {

    @Override
    public int _(final Pointer<?> arg0) {
        return 0;
    }
}
