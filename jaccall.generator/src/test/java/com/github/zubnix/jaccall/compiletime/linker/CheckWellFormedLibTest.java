package com.github.zubnix.jaccall.compiletime.linker;


import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class CheckWellFormedLibTest {

    @Test
    public void testNotAPrimitive() {
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
                 .in(fileObject)
                 .onLine(8)
                 .atColumn(31);
    }

    @Test
    public void testPtrAnnotationNotOnPrimitiveLong() {
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
                 .in(fileObject)
                 .onLine(11)
                 .atColumn(64);
    }
}