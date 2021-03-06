package org.freedesktop.jaccall.compiletime;


import com.google.testing.compile.CompileTester;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class CheckWellFormedFunctorTest {

    @Test
    public void testNotAnInterface() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public abstract class Testing {\n" +
                                                                          "    abstract long $(long tst,\n" +
                                                                          "                    byte field0,\n" +
                                                                          "                    @Unsigned short field1,\n" +
                                                                          "                    @Ptr(int.class) long field2,\n" +
                                                                          "                    @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("Type must be an interface.")
                     .in(fileObject);
    }

    @Test
    public void testIllegalExtends() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import java.util.Observer;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing extends Observer{\n" +
                                                                          "    long $(long tst,\n" +
                                                                          "           byte field0,\n" +
                                                                          "           @Unsigned short field1,\n" +
                                                                          "           @Ptr(int.class) long field2,\n" +
                                                                          "           @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("Type may not extend other interfaces.")
                     .in(fileObject);
    }

    @Test
    public void testDoesNotHaveSingleMethod() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing {\n" +
                                                                          "    long $(long tst,\n" +
                                                                          "                      byte field0,\n" +
                                                                          "                      @Unsigned short field1,\n" +
                                                                          "                      @Ptr(int.class) long field2,\n" +
                                                                          "                      @Ptr(int.class) long field3);\n" +
                                                                          "    long doStaticTest2(long tst,\n" +
                                                                          "                      byte field0,\n" +
                                                                          "                      @Unsigned short field1,\n" +
                                                                          "                      @Ptr(int.class) long field2,\n" +
                                                                          "                      @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("Type must have exactly one method.")
                     .in(fileObject);
    }

    @Test
    public void testNotDollarAsName() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing {\n" +
                                                                          "    long doStaticTest(long tst,\n" +
                                                                          "                      byte field0,\n" +
                                                                          "                      @Unsigned short field1,\n" +
                                                                          "                      @Ptr(int.class) long field2,\n" +
                                                                          "                      @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("Method name must be 'invoke'.")
                     .in(fileObject);
    }

    @Test
    public void testNotAPrimitive() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing {\n" +
                                                                          "    Long invoke(" + // <----
                                                                          "           long tst,\n" +
                                                                          "           byte field0,\n" +
                                                                          "           @Unsigned short field1,\n" +
                                                                          "           @Ptr(int.class) long field2,\n" +
                                                                          "           @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("Method should have supported primitive types only.")
                     .in(fileObject);
    }

    @Test
    public void testPtrAnnotationNotOnPrimitiveLong() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing {\n" +
                                                                          "    long $(long tst,\n" +
                                                                          "           byte field0,\n" +
                                                                          "           @Unsigned short field1,\n" +
                                                                          "           @Ptr(int.class) int field2,\n" + // <----
                                                                          "           @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Ptr annotation can only be placed on primitive type 'long'.")
                     .in(fileObject);
    }

    @Test
    public void testByValAnnotationNotOnPrimitiveLong() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.ByVal;\n" +
                                                                          "import org.freedesktop.jaccall.StructType;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing {\n" +
                                                                          "    long $(long tst,\n" +
                                                                          "           byte field0,\n" +
                                                                          "           @Unsigned short field1,\n" +
                                                                          "           @ByVal(StructType.class) int field2,\n" + // <----
                                                                          "           @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@ByVal annotation can only be placed on primitive type 'long'.")
                     .in(fileObject);
    }

    @Test
    public void testByValAnnotationInConjunctionWithPtrAnnotation() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.ByVal;\n" +
                                                                          "import org.freedesktop.jaccall.StructType;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing {\n" +
                                                                          "    long $(long tst,\n" +
                                                                          "           byte field0,\n" +
                                                                          "           @Unsigned short field1,\n" +
                                                                          "           @ByVal(StructType.class) @Ptr(long.class) long field2,\n" + // <----
                                                                          "           @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@ByVal annotation can not be placed in conjunction with @Ptr annotation.")
                     .in(fileObject);
    }

    @Test
    public void testByValAnnotationInConjunctionWithUnsignedAnnotation() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.ByVal;\n" +
                                                                          "import org.freedesktop.jaccall.StructType;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing {\n" +
                                                                          "    long $(long tst,\n" +
                                                                          "           byte field0,\n" +
                                                                          "           @Unsigned short field1,\n" +
                                                                          "           @ByVal(StructType.class) @Unsigned long field2,\n" + // <----
                                                                          "           @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@ByVal annotation can not be placed in conjunction with @Unsigned annotation.")
                     .in(fileObject);
    }

    @Test
    public void testLngAnnotationNotOnPrimitiveLong() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.Lng;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing {\n" +
                                                                          "    long $(long tst,\n" +
                                                                          "           byte field0,\n" +
                                                                          "           @Unsigned short field1,\n" +
                                                                          "           @Lng int field2,\n" + // <----
                                                                          "           @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Lng annotation can only be placed on primitive type 'long'.")
                     .in(fileObject);
    }

    @Test
    public void testLngAnnotationInConjunctionWithPtrAnnotation() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.Lng;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing {\n" +
                                                                          "    long $(long tst,\n" +
                                                                          "           byte field0,\n" +
                                                                          "           @Unsigned short field1,\n" +
                                                                          "           @Lng @Ptr(long.class) long field2,\n" + // <----
                                                                          "           @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Lng annotation can not be placed in conjunction with @Ptr annotation.")
                     .in(fileObject);
    }

    @Test
    public void testLngAnnotationInConjunctionWithByValAnnotation() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.ByVal;\n" +
                                                                          "import org.freedesktop.jaccall.StructType;\n" +
                                                                          "import org.freedesktop.jaccall.Lng;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing {\n" +
                                                                          "    long $(long tst,\n" +
                                                                          "           byte field0,\n" +
                                                                          "           @Unsigned short field1,\n" +
                                                                          "           @Lng @ByVal(StructType.class) long field2,\n" + // <----
                                                                          "           @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Lng annotation can not be placed in conjunction with @ByVal annotation.")
                     .in(fileObject);
    }

    @Test
    public void testUnsignedAnnotationNotOnPrimitiveNonFloat() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing {\n" +
                                                                          "    long $(long tst,\n" +
                                                                          "           byte field0,\n" +
                                                                          "           @Unsigned short field1,\n" +
                                                                          "           @Unsigned float field2,\n" + // <----
                                                                          "           @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Unsigned annotation can not be placed on primitive type 'float'.")
                     .in(fileObject);
    }

    @Test
    public void testUnsignedAnnotationNotOnPrimitiveNonDouble() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing {\n" +
                                                                          "    long $(long tst,\n" +
                                                                          "           byte field0,\n" +
                                                                          "           @Unsigned short field1,\n" +
                                                                          "           @Unsigned double field2,\n" +// <----
                                                                          "           @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Unsigned annotation can not be placed on primitive type 'double'.")
                     .in(fileObject);
    }

    @Test
    public void testUnsignedAnnotationInConjunctionWithPtrAnnotation() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("Testing",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "import org.freedesktop.jaccall.Functor;\n" +
                                                                          "import org.freedesktop.jaccall.Ptr;\n" +
                                                                          "import org.freedesktop.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface Testing {\n" +
                                                                          "    long $(long tst,\n" +
                                                                          "           byte field0,\n" +
                                                                          "           @Unsigned short field1,\n" +
                                                                          "           @Unsigned @Ptr(long.class) long field2,\n" + // <----
                                                                          "           @Ptr(int.class) long field3);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Unsigned annotation can not be placed in conjunction with @Ptr annotation.")
                     .in(fileObject);
    }
}