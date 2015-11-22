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
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{1},new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,JNI.FFI_TYPE_SINT8)},new String[]{\"(B)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedCharParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,JNI.FFI_TYPE_UINT8)},\n" +
                                                                       "        new String[]{\"(B)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testShortParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,JNI.FFI_TYPE_SINT16)},\n" +
                                                                       "        new String[]{\"(S)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedShortParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,JNI.FFI_TYPE_UINT16)},\n" +
                                                                       "        new String[]{\"(S)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testIntParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,JNI.FFI_TYPE_SINT32)},\n" +
                                                                       "        new String[]{\"(I)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedIntParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,JNI.FFI_TYPE_UINT32)},\n" +
                                                                       "        new String[]{\"(I)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testLongParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,JNI.FFI_TYPE_SLONG)},\n" +
                                                                       "        new String[]{\"(J)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedLongParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,JNI.FFI_TYPE_ULONG)},\n" +
                                                                       "        new String[]{\"(J)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testLongLongParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,JNI.FFI_TYPE_SINT64)},\n" +
                                                                       "        new String[]{\"(J)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedLongLongParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,JNI.FFI_TYPE_UINT64)},\n" +
                                                                       "        new String[]{\"(J)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testFloatParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,JNI.FFI_TYPE_FLOAT)},\n" +
                                                                       "        new String[]{\"(F)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testDoubleParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,JNI.FFI_TYPE_DOUBLE)},\n" +
                                                                       "        new String[]{\"(D)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testPointerParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,JNI.FFI_TYPE_POINTER)},\n" +
                                                                       "        new String[]{\"(J)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testStructByValParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import com.github.zubnix.jaccall.compiletime.linker.TestStruct;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,TestStruct.FFI_TYPE)},\n" +
                                                                       "        new String[]{\"(J)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnionByValParameterGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;\n" +
                                                                          "import com.github.zubnix.jaccall.ByVal;\n" +
                                                                          "import com.github.zubnix.jaccall.compiletime.linker.TestUnion;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    public static native void doStaticTest(@ByVal(TestUnion.class) long field0);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import com.github.zubnix.jaccall.compiletime.linker.TestUnion;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},\n" +
                                                                       "        new byte[]{1},\n" +
                                                                       "        new long[]{JNI.ffi_callInterface(JNI.FFI_TYPE_VOID,TestUnion.FFI_TYPE)},\n" +
                                                                       "        new String[]{\"(J)V\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testCharReturnTypeGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
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
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
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
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols_Jaccall_LinkSymbols",
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
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    public static native short doStaticTest();\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"s\"},new String[]{\"()S\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedShortReturnTypeGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;\n" +
                                                                          "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    @Unsigned" +
                                                                          "    public static native short doStaticTest();\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"S\"},new String[]{\"()S\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testIntReturnTypeGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    public static native int doStaticTest();\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"i\"},new String[]{\"()I\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedIntReturnTypeGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;\n" +
                                                                          "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    @Unsigned" +
                                                                          "    public static native int doStaticTest();\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"I\"},new String[]{\"()I\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testLongReturnTypeGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    public static native long doStaticTest();\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"j\"},new String[]{\"()J\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedLongReturnTypeGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;\n" +
                                                                          "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    @Unsigned" +
                                                                          "    public static native long doStaticTest();\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"J\"},new String[]{\"()J\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testLongLongReturnTypeGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;" +
                                                                          "import com.github.zubnix.jaccall.Lng;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    @Lng" +
                                                                          "    public static native long doStaticTest();\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"l\"},new String[]{\"()J\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedLongLongReturnTypeGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;" +
                                                                          "import com.github.zubnix.jaccall.Lng;\n" +
                                                                          "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    @Unsigned @Lng" +
                                                                          "    public static native long doStaticTest();\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"L\"},new String[]{\"()J\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testFloatReturnTypeGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    public static native float doStaticTest();\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"f\"},new String[]{\"()F\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testDoubleReturnTypeGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    public static native double doStaticTest();\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"d\"},new String[]{\"()D\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testPointerReturnTypeGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;\n" +
                                                                          "import com.github.zubnix.jaccall.Ptr;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    @Ptr" +
                                                                          "    public static native long doStaticTest();\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"p\"},new String[]{\"()J\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testStructByValReturnTypeGeneration() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;\n" +
                                                                          "import com.github.zubnix.jaccall.ByVal;\n" +
                                                                          "import com.github.zubnix.jaccall.compiletime.linker.TestStruct;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    @ByVal(TestStruct.class)\n" +
                                                                          "    public static native long doStaticTest();\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\"},new byte[]{0},new String[]{\"tcSiiiptlf]]\"},new String[]{\"()J\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testAllMixed() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing",
                                                                          "package com.github.zubnix.libtest;\n" +
                                                                          "import com.github.zubnix.jaccall.Lib;\n" +
                                                                          "import com.github.zubnix.jaccall.ByVal;\n" +
                                                                          "import com.github.zubnix.jaccall.Unsigned;\n" +
                                                                          "import com.github.zubnix.jaccall.Ptr;\n" +
                                                                          "import com.github.zubnix.jaccall.Lng;\n" +
                                                                          "import com.github.zubnix.jaccall.compiletime.linker.TestStruct;\n" +
                                                                          "\n" +
                                                                          "@Lib(\"testing\")\n" +
                                                                          "public class Testing {\n" +
                                                                          "    @ByVal(TestStruct.class)\n" +
                                                                          "    public static native long doStaticTest(@Unsigned int arg0, float arg1, double arg2, byte arg3);\n" +
                                                                          "\n" +
                                                                          "    @Unsigned" +
                                                                          "    public native long doTest(@Ptr long arg0, @Lng long arg1, @ByVal(TestStruct.class) long arg2);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new LinkerGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.Testing_Jaccall_LinkSymbols",
                                                                       "package com.github.zubnix.libtest;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{\"doStaticTest\",\"doTest\"},new byte[]{4,3},new String[]{\"IfdctcSiiiptlf]]\",\"pltcSiiiptlf]]J\"},new String[]{\"(IFDB)J\",\"(JJJ)J\"});\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }
}