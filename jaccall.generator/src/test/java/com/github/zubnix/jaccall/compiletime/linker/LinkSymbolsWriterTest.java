package com.github.zubnix.jaccall.compiletime.linker;


import com.google.testing.compile.CompileTester;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class LinkSymbolsWriterTest {

    @Test
    public void testCharParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(byte field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"cv\"},new String[]{\"(B)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedCharParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(@Unsigned byte field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"Cv\"},new String[]{\"(B)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testShortParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(short field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"sv\"},new String[]{\"(S)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedShortParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(@Unsigned short field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"Sv\"},new String[]{\"(S)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testIntParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(int field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"iv\"},new String[]{\"(I)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedIntParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(@Unsigned int field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"Iv\"},new String[]{\"(I)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testLongParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(long field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"jv\"},new String[]{\"(J)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedLongParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(@Unsigned long field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"Jv\"},new String[]{\"(J)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testLongLongParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "import com.github.zubnix.jaccall.Lng;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(@Lng long field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"lv\"},new String[]{\"(J)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedLongLongParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "import com.github.zubnix.jaccall.Lng;\n" +
                                                                    "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(@Unsigned @Lng long field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"Lv\"},new String[]{\"(J)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testFloatParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(float field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"fv\"},new String[]{\"(F)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testDoubleParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(double field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"dv\"},new String[]{\"(D)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testPointerParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "import com.github.zubnix.jaccall.Ptr;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(@Ptr long field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"pv\"},new String[]{\"(J)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testStructByValParameterGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "import com.github.zubnix.jaccall.ByVal;\n" +
                                                                    "import com.github.zubnix.jaccall.compiletime.linker.TestStruct;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native void doStaticTest(@ByVal(TestStruct.class) long field0);\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new String[]{\"tcSiiiptlf]]v\"},new String[]{\"(J)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testCharReturnTypeGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    public static native byte doStaticTest();\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"c\"},new String[]{\"()B\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedCharReturnTypeGeneration() {
        //given
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                    "package com.github.zubnix.libtest;\n" +
                                                                    "import com.github.zubnix.jaccall.Lib;\n" +
                                                                    "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                    "\n" +
                                                                    "@Lib(\"testing\")\n" +
                                                                    "public class Testing {\n" +
                                                                    "    @Unsigned" +
                                                                    "    public static native byte doStaticTest();\n" +
                                                                    "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"C\"},new String[]{\"()B\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testShortReturnTypeGeneration() {

    }

    @Test
    public void testUnsignedShortReturnTypeGeneration() {

    }

    @Test
    public void testIntReturnTypeGeneration() {

    }

    @Test
    public void testUnsignedIntReturnTypeGeneration() {

    }

    @Test
    public void testLongReturnTypeGeneration() {

    }

    @Test
    public void testUnsignedLongReturnTypeGeneration() {

    }

    @Test
    public void testUnsignedLongLongReturnTypeGeneration() {

    }

    @Test
    public void testFloatReturnTypeGeneration() {

    }

    @Test
    public void testDoubleReturnTypeGeneration() {

    }

    @Test
    public void testPointerReturnTypeGeneration() {

    }

    @Test
    public void testStructByValReturnTypeGeneration() {

    }
}