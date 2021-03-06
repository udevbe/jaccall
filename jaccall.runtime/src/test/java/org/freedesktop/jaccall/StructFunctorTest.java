package org.freedesktop.jaccall;

import org.freedesktop.libtest.TestStructFunctionPointer;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.freedesktop.jaccall.Pointer.wrap;

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
        final long field0Result = structFpPointer.get()
                                                 .field0()
                                                 .get()
                                                 .invoke(123456);
        final long field10Result = structFpPointer.get()
                                                  .field1()
                                                  .get(0)
                                                  .get()
                                                  .invoke(123456);
        final long field11Result = structFpPointer.get()
                                                  .field1()
                                                  .get(1)
                                                  .get()
                                                  .invoke(123456);

        final long field12Result = structFpPointer.get()
                                                  .field1()
                                                  .get(2)
                                                  .get()
                                                  .invoke(123456);


        //then
        //functions are invoked and return the correct result
        assertThat(field0Result).isEqualTo(123456);
        assertThat(field10Result).isEqualTo(123456 + 0);
        assertThat(field11Result).isEqualTo(123456 + 1);
        assertThat(field12Result).isEqualTo(123456 + 2);
    }
}
