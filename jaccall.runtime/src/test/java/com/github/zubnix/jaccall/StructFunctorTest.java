package com.github.zubnix.jaccall;

import com.github.zubnix.libtest.TestStructFunctionPointer;
import org.junit.Test;

import static com.github.zubnix.jaccall.Pointer.wrap;
import static com.google.common.truth.Truth.assertThat;

public class StructFunctorTest {

    @Test
    public void testStructFunctionPointer() {
        //given
        //natively allocated struct with function pointers as fields
        final long structFp = JNITestUtil.allocStructFp();
        final Pointer<TestStructFunctionPointer> structFpPointer = wrap(TestStructFunctionPointer.class,
                                                                        structFp);

        //when
        //function pointers are called in java
        final long field0Result = structFpPointer.dref()
                                                 .field0()
                                                 .dref()
                                                 .$(123456);
        final long field10Result = structFpPointer.dref()
                                                  .field1()
                                                  .dref(0)
                                                  .dref()
                                                  .$(123456);
        final long field11Result = structFpPointer.dref()
                                                  .field1()
                                                  .dref(1)
                                                  .dref()
                                                  .$(123456);

        final long field12Result = structFpPointer.dref()
                                                  .field1()
                                                  .dref(2)
                                                  .dref()
                                                  .$(123456);


        //then
        //functions are invoked and return the correct result
        assertThat(field0Result).isEqualTo(123456);
        assertThat(field10Result).isEqualTo(123456 + 0);
        assertThat(field11Result).isEqualTo(123456 + 1);
        assertThat(field12Result).isEqualTo(123456 + 2);
    }
}
