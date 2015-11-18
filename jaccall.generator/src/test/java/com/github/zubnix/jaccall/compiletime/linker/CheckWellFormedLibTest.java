package com.github.zubnix.jaccall.compiletime.linker;


import com.github.zubnix.jaccall.compiletime.linker.testcasesource.LibNotOnClass;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class CheckWellFormedLibTest {

    @Test
    public void testLibNotOnClass() {
        JavaFileObject fileObject = TestCaseUtil.get(LibNotOnClass.class);
        assert_().about(javaSource())
                 .that(fileObject)
                 .processedWith(new LinkerGenerator())
                 .failsToCompile()
                 .withErrorContaining("@Lib annotation should be placed on class type only.")
                 .in(fileObject);
    }

    @Test
    public void testNotAPrimitive() {
        //TODO move to it's own source file to catch compile time errors early
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "import com.github.zubnix.jaccall.Ptr;\n" +
                                                                    "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native Long doStaticTest(long tst,\n" +
                                                                    "                                           byte field0,\n" +
                                                                    "                                           @Unsigned short field1,\n" +
                                                                    "                                           @Ptr(int.class) long field2,\n" +
                                                                    "                                           @Ptr(int.class) long field3);\n" +
                                                                    "}");
        assert_().about(javaSource())
                 .that(fileObject)
                 .processedWith(new LinkerGenerator())
                 .failsToCompile()
                 .withErrorContaining("Native method should have supported primitive types only.")
                 .in(fileObject);
    }

    @Test
    public void testPtrAnnotationNotOnPrimitiveLong() {
        //TODO move to it's own source file to catch compile time errors early
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "import com.github.zubnix.jaccall.Ptr;\n" +
                                                                    "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native long doStaticTest(long tst,\n" +
                                                                    "                                           byte field0,\n" +
                                                                    "                                           @Unsigned short field1,\n" +
                                                                    "                                           @Ptr(int.class) int field2,\n" +
                                                                    "                                           @Ptr(int.class) long field3);\n" +
                                                                    "}");
        assert_().about(javaSource())
                 .that(fileObject)
                 .processedWith(new LinkerGenerator())
                 .failsToCompile()
                 .withErrorContaining("@Ptr annotation can only be placed on primitive type 'long'.")
                 .in(fileObject);
    }

    @Test
    public void testByValAnnotationNotOnPrimitiveLong() {
        //TODO move to it's own source file to catch compile time errors early
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "import com.github.zubnix.jaccall.ByVal;\n" +
                                                                    "import com.github.zubnix.jaccall.StructType;\n" +
                                                                    "import com.github.zubnix.jaccall.Ptr;\n" +
                                                                    "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native long doStaticTest(long tst,\n" +
                                                                    "                                           byte field0,\n" +
                                                                    "                                           @Unsigned short field1,\n" +
                                                                    "                                           @ByVal(StructType.class) int field2,\n" +
                                                                    "                                           @Ptr(int.class) long field3);\n" +
                                                                    "}");
        assert_().about(javaSource())
                 .that(fileObject)
                 .processedWith(new LinkerGenerator())
                 .failsToCompile()
                 .withErrorContaining("@ByVal annotation can only be placed on primitive type 'long'.")
                 .in(fileObject);
    }


}