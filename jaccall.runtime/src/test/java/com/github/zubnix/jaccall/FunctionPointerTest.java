//package com.github.zubnix.jaccall;
//
//
//import com.github.zubnix.libtest.TestFunction;
//import org.junit.Test;
//
//public class FunctionPointerTest {
//
//    @Test
//    public void testCallFromC() {
//
//        final long funcPtrAddr = 1235L;
//        final Pointer<TestFunction> testFunctionPointer = Pointer.wrap(TestFunction.class,
//                                                                       funcPtrAddr);
//        testFunctionPointer.dref()
//                           .$(123);
//    }
//
//    @Test
//    public void testCallFromJava() {
//
//        final TestFunction testFunction = new TestFunction() {
//            @Override
//            public int $(@Ptr final long arg0) {
//                return 9876;
//            }
//        };
//
//        // final Pointer<TestFunction> testFunctionPointer = Pointer.nref(testFunction);
//
//
//    }
//}
