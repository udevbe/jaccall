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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, JNI.FFI_TYPE_SINT8)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(B)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, JNI.FFI_TYPE_UINT8)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(B)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, JNI.FFI_TYPE_SINT16)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(S)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, JNI.FFI_TYPE_UINT16)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(S)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, JNI.FFI_TYPE_SINT32)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(I)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, JNI.FFI_TYPE_UINT32)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(I)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, JNI.FFI_TYPE_SLONG)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(J)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, JNI.FFI_TYPE_ULONG)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(J)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, JNI.FFI_TYPE_SINT64)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(J)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, JNI.FFI_TYPE_UINT64)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(J)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, JNI.FFI_TYPE_FLOAT)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(F)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, JNI.FFI_TYPE_DOUBLE)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(D)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, JNI.FFI_TYPE_POINTER)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(J)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, TestStruct.FFI_TYPE)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(J)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 1\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_VOID, TestUnion.FFI_TYPE)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(J)V\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_SINT8)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()B\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_UINT8)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()B\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_SINT16)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()S\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_UINT16)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()S\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_SINT32)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()I\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_UINT32)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()I\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_SLONG)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()J\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_ULONG)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()J\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_SINT64)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()J\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_UINT64)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()J\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_FLOAT)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()F\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_DOUBLE)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()D\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_POINTER)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()J\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import com.github.zubnix.jaccall.compiletime.linker.TestStruct;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 0\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(TestStruct.FFI_TYPE)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"()J\"\n" +
                                                                       "        });\n" +
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
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.LinkSymbols;\n" +
                                                                       "import com.github.zubnix.jaccall.compiletime.linker.TestStruct;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.linker.LinkerGenerator\")\n" +
                                                                       "public final class Testing_Jaccall_LinkSymbols extends LinkSymbols {\n" +
                                                                       "  public Testing_Jaccall_LinkSymbols() {\n" +
                                                                       "    super(new String[]{ /*method name*/\n" +
                                                                       "        \"doStaticTest\",\n" +
                                                                       "        \"doTest\"\n" +
                                                                       "        },\n" +
                                                                       "        new byte[]{ /*number of arguments*/\n" +
                                                                       "        /*doStaticTest*/ 4,\n" +
                                                                       "        /*doTest*/ 3\n" +
                                                                       "        },\n" +
                                                                       "        new long[]{ /*FFI call interface*/\n" +
                                                                       "        /*doStaticTest*/ JNI.ffi_callInterface(TestStruct.FFI_TYPE, JNI.FFI_TYPE_UINT32, JNI.FFI_TYPE_FLOAT, JNI.FFI_TYPE_DOUBLE, JNI.FFI_TYPE_SINT8),\n" +
                                                                       "        /*doTest*/ JNI.ffi_callInterface(JNI.FFI_TYPE_ULONG, JNI.FFI_TYPE_POINTER, JNI.FFI_TYPE_SINT64, TestStruct.FFI_TYPE)\n" +
                                                                       "        },\n" +
                                                                       "        new String[]{ /*JNI method signature*/\n" +
                                                                       "        /*doStaticTest*/ \"(IFDB)J\",\n" +
                                                                       "        /*doTest*/ \"(JJJ)J\"\n" +
                                                                       "        });\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }
}