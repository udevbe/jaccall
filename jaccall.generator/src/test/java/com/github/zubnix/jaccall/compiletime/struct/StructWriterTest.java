package com.github.zubnix.jaccall.compiletime.struct;


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
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStructChar",
                                                                          "package com.github.zubnix.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.CHAR;\n" +
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
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStructChar_Jaccall_StructType",
                                                                       "package com.github.zubnix.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.StructType;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.struct.StructGenerator\")\n" +
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
                                                                       "    return readByte(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final byte field0) {\n" +
                                                                       "    writeByte(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testUnsignedCharField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStructUnsignedChar",
                                                                          "package com.github.zubnix.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.UNSIGNED_CHAR;\n" +
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
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStructUnsignedChar_Jaccall_StructType",
                                                                       "package com.github.zubnix.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.StructType;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.struct.StructGenerator\")\n" +
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
                                                                       "    return readByte(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final byte field0) {\n" +
                                                                       "    writeByte(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testShortField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStructShort",
                                                                          "package com.github.zubnix.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.SHORT;\n" +
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
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStructShort_Jaccall_StructType",
                                                                       "package com.github.zubnix.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.StructType;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.struct.StructGenerator\")\n" +
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
                                                                       "    return readShort(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final short field0) {\n" +
                                                                       "    writeShort(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));

    }

    @Test
    public void testUnsignedShortField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStructUnsignedShort",
                                                                          "package com.github.zubnix.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.UNSIGNED_SHORT;\n" +
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
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStructUnsignedShort_Jaccall_StructType",
                                                                       "package com.github.zubnix.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.StructType;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.struct.StructGenerator\")\n" +
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
                                                                       "    return readShort(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final short field0) {\n" +
                                                                       "    writeShort(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));

    }

    @Test
    public void testIntegerField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStructInteger",
                                                                          "package com.github.zubnix.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.INT;\n" +
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
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStructInteger_Jaccall_StructType",
                                                                       "package com.github.zubnix.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.StructType;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.struct.StructGenerator\")\n" +
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
                                                                       "    return readInteger(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final int field0) {\n" +
                                                                       "    writeInteger(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));

    }

    @Test
    public void testUnsignedIntegerField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStructUnsignedInteger",
                                                                          "package com.github.zubnix.libtest.struct;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.UNSIGNED_INT;\n" +
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
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStructUnsignedInteger_Jaccall_StructType",
                                                                       "package com.github.zubnix.libtest.struct;\n" +
                                                                       "\n" +
                                                                       "import com.github.zubnix.jaccall.JNI;\n" +
                                                                       "import com.github.zubnix.jaccall.StructType;\n" +
                                                                       "import javax.annotation.Generated;\n" +
                                                                       "\n" +
                                                                       "@Generated(\"com.github.zubnix.jaccall.compiletime.struct.StructGenerator\")\n" +
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
                                                                       "    return readInteger(OFFSET_0);\n" +
                                                                       "  }\n" +
                                                                       "\n" +
                                                                       "  public final void field0(final int field0) {\n" +
                                                                       "    writeInteger(OFFSET_0, field0);\n" +
                                                                       "  }\n" +
                                                                       "}"));
    }

    @Test
    public void testLongField() {
        //TODO

    }

    @Test
    public void testUnsignedLongField() {
        //TODO

    }

    @Test
    public void testLongLongField() {
        //TODO

    }

    @Test
    public void testUnsignedLongLongField() {
        //TODO

    }

    @Test
    public void testFloatField() {
        //TODO

    }

    @Test
    public void testDoubleField() {
        //TODO

    }

    @Test
    public void testPointerField() {
        //TODO

    }

    @Test
    public void testStructField() {
        //TODO

    }

    @Test
    public void testMixed() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.FieldsTestStruct",
                                                                          "package com.github.zubnix.libtest.struct;\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "import com.github.zubnix.jaccall.compiletime.linker.TestStructEmbedded;\n" +
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
                                                     .processedWith(new StructGenerator());
        //then
        //TODO
    }
}