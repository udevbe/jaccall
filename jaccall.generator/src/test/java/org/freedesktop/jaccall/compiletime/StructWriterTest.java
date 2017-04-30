package org.freedesktop.jaccall.compiletime;


import com.google.testing.compile.CompileTester;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class StructWriterTest {

    @Test
    public void testCharField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructChar",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.CHAR;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = CHAR,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructChar extends TestStructChar_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructChar_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructChar_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_SINT8);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStruct_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final byte field0() {\n" +
                                                                       "    return getByte(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final byte field0) {\n" +
                                                                       "    setByte(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedCharField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructUnsignedChar",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.UNSIGNED_CHAR;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = UNSIGNED_CHAR,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructUnsignedChar extends TestStructUnsignedChar_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructUnsignedChar_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructUnsignedChar_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_UINT8);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStructUnsignedChar_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final byte field0() {\n" +
                                                                       "    return getByte(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final byte field0) {\n" +
                                                                       "    setByte(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testShortField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructShort",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.SHORT;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = SHORT,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructShort extends TestStructShort_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructShort_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructShort_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_SINT16);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStructShort_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final short field0() {\n" +
                                                                       "    return getShort(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final short field0) {\n" +
                                                                       "    setShort(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));

    }

    @Test
    public void testUnsignedShortField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructUnsignedShort",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.UNSIGNED_SHORT;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = UNSIGNED_SHORT,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructUnsignedShort extends TestStructUnsignedShort_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructUnsignedShort_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructUnsignedShort_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_UINT16);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStructUnsignedShort_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final short field0() {\n" +
                                                                       "    return getShort(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final short field0) {\n" +
                                                                       "    setShort(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));

    }

    @Test
    public void testIntegerField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructInteger",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.INT;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = INT,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructInteger extends TestStructInteger_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructInteger_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructInteger_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_SINT32);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStructInteger_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final int field0() {\n" +
                                                                       "    return getInteger(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final int field0) {\n" +
                                                                       "    setInteger(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));

    }

    @Test
    public void testUnsignedIntegerField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructUnsignedInteger",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.UNSIGNED_INT;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = UNSIGNED_INT,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructUnsignedInteger extends TestStructUnsignedInteger_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructUnsignedInteger_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructUnsignedInteger_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_UINT32);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStructUnsignedInteger_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final int field0() {\n" +
                                                                       "    return getInteger(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final int field0) {\n" +
                                                                       "    setInteger(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testLongField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructLong",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.LONG;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = LONG,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructLong extends TestStructLong_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructLong_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.CLong;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructLong_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_SLONG);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStructLong_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final CLong field0() {\n" +
                                                                       "    return getCLong(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final CLong field0) {\n" +
                                                                       "    setCLong(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedLongField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructUnsignedLong",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.UNSIGNED_LONG;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = UNSIGNED_LONG,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructUnsignedLong extends TestStructUnsignedLong_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructUnsignedLong_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.CLong;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructUnsignedLong_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_ULONG);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStructUnsignedLong_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final CLong field0() {\n" +
                                                                       "    return getCLong(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final CLong field0) {\n" +
                                                                       "    setCLong(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testLongLongField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructLongLong",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.LONG_LONG;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = LONG_LONG,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructLongLong extends TestStructLongLong_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructLongLong_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructLongLong_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_SINT64);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStructLongLong_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final long field0() {\n" +
                                                                       "    return getLong(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final long field0) {\n" +
                                                                       "    setLong(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedLongLongField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructUnsignedLongLong",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.UNSIGNED_LONG_LONG;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = UNSIGNED_LONG_LONG,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructUnsignedLongLong extends TestStructUnsignedLongLong_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructUnsignedLongLong_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructUnsignedLongLong_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_UINT64);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStructUnsignedLongLong_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final long field0() {\n" +
                                                                       "    return getLong(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final long field0) {\n" +
                                                                       "    setLong(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testFloatField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructFloat",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.FLOAT;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = FLOAT,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructFloat extends TestStructFloat_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructFloat_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructFloat_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_FLOAT);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStructFloat_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final float field0() {\n" +
                                                                       "    return getFloat(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final float field0) {\n" +
                                                                       "    setFloat(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testDoubleField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructDouble",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.DOUBLE;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = DOUBLE,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructDouble extends TestStructDouble_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructDouble_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructDouble_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_DOUBLE);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStructDouble_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final double field0() {\n" +
                                                                       "    return getDouble(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final double field0) {\n" +
                                                                       "    setDouble(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testPointerField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructPointer",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.POINTER;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = POINTER,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructPointer extends TestStructPointer_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructPointer_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import java.lang.Void;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.Pointer;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructPointer_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_POINTER);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStructPointer_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final Pointer<Void> field0() {\n" +
                                                                       "    return getPointer(OFFSET_0, Void.class);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final Pointer<Void> field0) {\n" +
                                                                       "    setPointer(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));

    }

    @Test
    public void testPointerToPointerToPointerField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructPointer",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.POINTER;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = POINTER,\n" +
                                                                          "                    pointerDepth = 2,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructPointer extends TestStructPointer_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructPointer_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import java.lang.Void;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.Pointer;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructPointer_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_POINTER);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  TestStructPointer_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final Pointer<Pointer<Pointer<Void>>> field0() {\n" +
                                                                       "    return getPointer(OFFSET_0, Void.class).castpp().castpp();\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final Pointer<Pointer<Pointer<Void>>> field0) {\n" +
                                                                       "    setPointer(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));

    }

    @Test
    public void testStructField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructStruct",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.compiletime.TestStructEmbedded;\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.STRUCT;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = STRUCT,\n" +
                                                                          "                    dataType = TestStructEmbedded.class,\n" +
                                                                          "                    name = \"field0\")})\n" +
                                                                          "public final class TestStructStruct extends TestStructStruct_Jaccall_StructType{\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStructStruct_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "import org.freedesktop.jaccall.compiletime.TestStructEmbedded;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class TestStructStruct_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(TestStructEmbedded.FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "  ;\n" +
                                                                       "\n" +
                                                                       "  TestStructStruct_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final TestStructEmbedded field0() {\n" +
                                                                       "    return getStructType(OFFSET_0, TestStructEmbedded.class);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final TestStructEmbedded field0) {\n" +
                                                                       "    setStructType(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testMixed() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.FieldsTestStruct",
                                                                          "package org.freedesktop.libtest.struct;\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "import org.freedesktop.jaccall.compiletime.TestStructEmbedded;\n" +
                                                                          "        @Struct({\n" +
                                                                          "                        @Field(name = \"charField\",\n" +
                                                                          "                               type = CType.CHAR),\n" +
                                                                          "                        @Field(name = \"shortField\",\n" +
                                                                          "                               type = CType.SHORT),\n" +
                                                                          "                        @Field(name = \"intField\",\n" +
                                                                          "                               type = CType.INT),\n" +
                                                                          "                        @Field(name = \"longField\",\n" +
                                                                          "                               type = CType.LONG),\n" +
                                                                          "                        @Field(name = \"longLongField\",\n" +
                                                                          "                               type = CType.LONG_LONG),\n" +
                                                                          "                        @Field(name = \"floatField\",\n" +
                                                                          "                               type = CType.FLOAT),\n" +
                                                                          "                        @Field(name = \"doubleField\",\n" +
                                                                          "                               type = CType.DOUBLE),\n" +
                                                                          "                        @Field(name = \"pointerField\",\n" +
                                                                          "                               type = CType.POINTER,\n" +
                                                                          "                               dataType = Void.class),\n" +
                                                                          "                        @Field(name = \"pointerArrayField\",\n" +
                                                                          "                               type = CType.POINTER,\n" +
                                                                          "                               dataType = Void.class,\n" +
                                                                          "                               cardinality = 3),\n" +
                                                                          "                        @Field(name = \"structField\",\n" +
                                                                          "                               type = CType.STRUCT,\n" +
                                                                          "                               dataType = TestStructEmbedded.class),\n" +
                                                                          "                        @Field(name = \"structArrayField\",\n" +
                                                                          "                               type = CType.STRUCT,\n" +
                                                                          "                               dataType = TestStructEmbedded.class,\n" +
                                                                          "                               cardinality = 3),\n" +
                                                                          "                })\n" +
                                                                          "        public final class FieldsTestStruct extends FieldsTestStruct_Jaccall_StructType {\n" +
                                                                          "        }");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.FieldsTestStruct_Jaccall_StructType",
                                                                       "package org.freedesktop.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import java.lang.Byte;\n" +
                                                                       "import java.lang.Double;\n" +
                                                                       "import java.lang.Float;\n" +
                                                                       "import java.lang.Integer;\n" +
                                                                       "import java.lang.Long;\n" +
                                                                       "import java.lang.Short;\n" +
                                                                       "import java.lang.Void;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "import org.freedesktop.jaccall.CLong;\n" +
                                                                       "import org.freedesktop.jaccall.JNI;\n" +
                                                                       "import org.freedesktop.jaccall.Pointer;\n" +
                                                                       "import org.freedesktop.jaccall.Size;\n" +
                                                                       "import org.freedesktop.jaccall.StructType;\n" +
                                                                       "import org.freedesktop.jaccall.Types;\n" +
                                                                       "import org.freedesktop.jaccall.compiletime.TestStructEmbedded;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                       "abstract class FieldsTestStruct_Jaccall_StructType extends StructType {\n" +
                                                                       "  public static final long FFI_TYPE = JNI.ffi_type_struct(JNI.FFI_TYPE_SINT8, JNI.FFI_TYPE_SINT16, JNI.FFI_TYPE_SINT32, JNI.FFI_TYPE_SLONG, JNI.FFI_TYPE_SINT64, JNI.FFI_TYPE_FLOAT, JNI.FFI_TYPE_DOUBLE, JNI.FFI_TYPE_POINTER, JNI.FFI_TYPE_POINTER, JNI.FFI_TYPE_POINTER, JNI.FFI_TYPE_POINTER, TestStructEmbedded.FFI_TYPE, TestStructEmbedded.FFI_TYPE, TestStructEmbedded.FFI_TYPE, TestStructEmbedded.FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  public static final int SIZE = JNI.ffi_type_struct_size(FFI_TYPE);\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_0 = 0;\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_1 = Types.newOffset(Types.alignment((Short) null), OFFSET_0 + (Size.sizeof((Byte) null) * 1));\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_2 = Types.newOffset(Types.alignment((Integer) null), OFFSET_1 + (Size.sizeof((Short) null) * 1));\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_3 = Types.newOffset(Types.alignment((CLong) null), OFFSET_2 + (Size.sizeof((Integer) null) * 1));\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_4 = Types.newOffset(Types.alignment((Long) null), OFFSET_3 + (Size.sizeof((CLong) null) * 1));\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_5 = Types.newOffset(Types.alignment((Float) null), OFFSET_4 + (Size.sizeof((Long) null) * 1));\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_6 = Types.newOffset(Types.alignment((Double) null), OFFSET_5 + (Size.sizeof((Float) null) * 1));\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_7 = Types.newOffset(Types.alignment((Pointer) null), OFFSET_6 + (Size.sizeof((Double) null) * 1));\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_8 = Types.newOffset(Types.alignment((Pointer) null), OFFSET_7 + (Size.sizeof((Pointer) null) * 1));\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_9 = Types.newOffset(Types.alignment((Long) null), OFFSET_8 + (Size.sizeof((Pointer) null) * 3));\n" +
                                                                       "\n" +
                                                                       "  private static final int OFFSET_10 = Types.newOffset(Types.alignment((Long) null), OFFSET_9 + (TestStructEmbedded.SIZE * 1));\n" +
                                                                       "\n" +
                                                                       "  FieldsTestStruct_Jaccall_StructType() {\n" +
                                                                       "    super(SIZE);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final byte charField() {\n" +
                                                                       "    return getByte(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void charField(final byte charField) {\n" +
                                                                       "    setByte(OFFSET_0, charField);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final short shortField() {\n" +
                                                                       "    return getShort(OFFSET_1);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void shortField(final short shortField) {\n" +
                                                                       "    setShort(OFFSET_1, shortField);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final int intField() {\n" +
                                                                       "    return getInteger(OFFSET_2);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void intField(final int intField) {\n" +
                                                                       "    setInteger(OFFSET_2, intField);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final CLong longField() {\n" +
                                                                       "    return getCLong(OFFSET_3);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void longField(final CLong longField) {\n" +
                                                                       "    setCLong(OFFSET_3, longField);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final long longLongField() {\n" +
                                                                       "    return getLong(OFFSET_4);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void longLongField(final long longLongField) {\n" +
                                                                       "    setLong(OFFSET_4, longLongField);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final float floatField() {\n" +
                                                                       "    return getFloat(OFFSET_5);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void floatField(final float floatField) {\n" +
                                                                       "    setFloat(OFFSET_5, floatField);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final double doubleField() {\n" +
                                                                       "    return getDouble(OFFSET_6);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void doubleField(final double doubleField) {\n" +
                                                                       "    setDouble(OFFSET_6, doubleField);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final Pointer<Void> pointerField() {\n" +
                                                                       "    return getPointer(OFFSET_7, Void.class);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void pointerField(final Pointer<Void> pointerField) {\n" +
                                                                       "    setPointer(OFFSET_7, pointerField);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final Pointer<Pointer<Void>> pointerArrayField() {\n" +
                                                                       "    return getArray(OFFSET_8, Void.class).castpp();\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final TestStructEmbedded structField() {\n" +
                                                                       "    return getStructType(OFFSET_9, TestStructEmbedded.class);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void structField(final TestStructEmbedded structField) {\n" +
                                                                       "    setStructType(OFFSET_9, structField);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final Pointer<TestStructEmbedded> structArrayField() {\n" +
                                                                       "    return getArray(OFFSET_10, TestStructEmbedded.class);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }
}