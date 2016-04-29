package org.freedesktop.jaccall.compiletime;


import com.google.testing.compile.CompileTester;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class CheckWellFormedStructTest {
    @Test
    public void testStructOnNestedClass() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.Upper",
                                                                          "package org.freedesktop.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.CHAR;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.INT;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.POINTER;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.UNSIGNED_SHORT;\n" +
                                                                          "\n" +
                                                                          "public class Upper {" +
                                                                          "     @Struct(value = {\n" +
                                                                          "             @Field(type = CHAR,\n" +
                                                                          "                    name = \"field0\"),\n" +
                                                                          "             @Field(type = UNSIGNED_SHORT,\n" +
                                                                          "                    name = \"field1\"),\n" +
                                                                          "             @Field(type = INT,\n" +
                                                                          "                    cardinality = 3,\n" +
                                                                          "                    name = \"field2\"),\n" +
                                                                          "             @Field(type = POINTER,\n" +
                                                                          "                    dataType = int.class,\n" +
                                                                          "                    name = \"field3\"),\n" +
                                                                          "             @Field(type = CType.STRUCT,\n" +
                                                                          "                    dataType = TestStructEmbedded.class,\n" +
                                                                          "                    name = \"field4\")\n" +
                                                                          "              })\n" +
                                                                          "      public final class TestStruct {\n" +
                                                                          "      \n" +
                                                                          "   }" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotation should be placed on top level class types only.")
                     .in(fileObject);
    }

    @Test
    public void testStructIsNotClass() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStruct",
                                                                          "package org.freedesktop.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.CHAR;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.INT;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.POINTER;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.UNSIGNED_SHORT;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = CHAR,\n" +
                                                                          "                    name = \"field0\"),\n" +
                                                                          "             @Field(type = UNSIGNED_SHORT,\n" +
                                                                          "                    name = \"field1\"),\n" +
                                                                          "             @Field(type = INT,\n" +
                                                                          "                    cardinality = 3,\n" +
                                                                          "                    name = \"field2\"),\n" +
                                                                          "             @Field(type = POINTER,\n" +
                                                                          "                    dataType = int.class,\n" +
                                                                          "                    name = \"field3\"),\n" +
                                                                          "             @Field(type = CType.STRUCT,\n" +
                                                                          "                    dataType = TestStructEmbedded.class,\n" +
                                                                          "                    name = \"field4\")\n" +
                                                                          "              })\n" +
                                                                          "public interface TestStruct {\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotation must be placed on a class type.")
                     .in(fileObject);
    }

    @Test
    public void testStructIsNotPublic() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStruct",
                                                                          "package org.freedesktop.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.CHAR;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.INT;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.POINTER;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.UNSIGNED_SHORT;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = CHAR,\n" +
                                                                          "                    name = \"field0\"),\n" +
                                                                          "             @Field(type = UNSIGNED_SHORT,\n" +
                                                                          "                    name = \"field1\"),\n" +
                                                                          "             @Field(type = INT,\n" +
                                                                          "                    cardinality = 3,\n" +
                                                                          "                    name = \"field2\"),\n" +
                                                                          "             @Field(type = POINTER,\n" +
                                                                          "                    dataType = int.class,\n" +
                                                                          "                    name = \"field3\"),\n" +
                                                                          "             @Field(type = CType.STRUCT,\n" +
                                                                          "                    dataType = TestStructEmbedded.class,\n" +
                                                                          "                    name = \"field4\")\n" +
                                                                          "              })\n" +
                                                                          "final class TestStruct extends TestStruct_Jaccall_StructType {\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotation must be placed on a public type.")
                     .in(fileObject);
    }

    @Test
    public void testStructNoDefaultConstructor() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStruct",
                                                                          "package org.freedesktop.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.CHAR;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.INT;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.POINTER;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.UNSIGNED_SHORT;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = CHAR,\n" +
                                                                          "                    name = \"field0\"),\n" +
                                                                          "             @Field(type = UNSIGNED_SHORT,\n" +
                                                                          "                    name = \"field1\"),\n" +
                                                                          "             @Field(type = INT,\n" +
                                                                          "                    cardinality = 3,\n" +
                                                                          "                    name = \"field2\"),\n" +
                                                                          "             @Field(type = POINTER,\n" +
                                                                          "                    dataType = int.class,\n" +
                                                                          "                    name = \"field3\"),\n" +
                                                                          "             @Field(type = CType.STRUCT,\n" +
                                                                          "                    dataType = TestStructEmbedded.class,\n" +
                                                                          "                    name = \"field4\")\n" +
                                                                          "              })\n" +
                                                                          "public final class TestStruct extends TestStruct_Jaccall_StructType {\n" +
                                                                          " \n" +
                                                                          "     public TestStruct(int foo) {" +
                                                                          "     \n" +
                                                                          "     }\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotated type must contain a public no-arg constructor.")
                     .in(fileObject);
    }

    @Test
    public void testStructNoFields() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStruct",
                                                                          "package org.freedesktop.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {})\n" +
                                                                          "public final class TestStruct extends TestStruct_Jaccall_StructType {\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotation must have at least one field.")
                     .in(fileObject);
    }

    @Test
    public void testStructStaticSIZEField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStruct",
                                                                          "package org.freedesktop.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.CHAR;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.INT;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.POINTER;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.UNSIGNED_SHORT;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = CHAR,\n" +
                                                                          "                    name = \"field0\"),\n" +
                                                                          "             @Field(type = UNSIGNED_SHORT,\n" +
                                                                          "                    name = \"field1\"),\n" +
                                                                          "             @Field(type = INT,\n" +
                                                                          "                    cardinality = 3,\n" +
                                                                          "                    name = \"field2\"),\n" +
                                                                          "             @Field(type = POINTER,\n" +
                                                                          "                    dataType = int.class,\n" +
                                                                          "                    name = \"field3\"),\n" +
                                                                          "             @Field(type = CType.STRUCT,\n" +
                                                                          "                    dataType = TestStructEmbedded.class,\n" +
                                                                          "                    name = \"field4\")\n" +
                                                                          "              })\n" +
                                                                          "public final class TestStruct extends TestStruct_Jaccall_StructType {\n" +
                                                                          "\n" +
                                                                          "     public static int SIZE = 1234;\n" +
                                                                          "\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct type may not contain a static field with name SIZE.")
                     .in(fileObject);
    }

//    @Test
//    public void testStructNotExtending() {
//        //given
//        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStruct",
//                                                                          "package org.freedesktop.jaccall.compiletime.linker;\n" +
//                                                                          "\n" +
//                                                                          "import org.freedesktop.jaccall.CType;\n" +
//                                                                          "import org.freedesktop.jaccall.Field;\n" +
//                                                                          "import org.freedesktop.jaccall.Struct;\n" +
//                                                                          "\n" +
//                                                                          "import static org.freedesktop.jaccall.CType.CHAR;\n" +
//                                                                          "import static org.freedesktop.jaccall.CType.INT;\n" +
//                                                                          "import static org.freedesktop.jaccall.CType.POINTER;\n" +
//                                                                          "import static org.freedesktop.jaccall.CType.UNSIGNED_SHORT;\n" +
//                                                                          "\n" +
//                                                                          "@Struct(value = {\n" +
//                                                                          "             @Field(type = CHAR,\n" +
//                                                                          "                    name = \"field0\"),\n" +
//                                                                          "             @Field(type = UNSIGNED_SHORT,\n" +
//                                                                          "                    name = \"field1\"),\n" +
//                                                                          "             @Field(type = INT,\n" +
//                                                                          "                    cardinality = 3,\n" +
//                                                                          "                    name = \"field2\"),\n" +
//                                                                          "             @Field(type = POINTER,\n" +
//                                                                          "                    dataType = int.class,\n" +
//                                                                          "                    name = \"field3\"),\n" +
//                                                                          "             @Field(type = CType.STRUCT,\n" +
//                                                                          "                    dataType = TestStructEmbedded.class,\n" +
//                                                                          "                    name = \"field4\")\n" +
//                                                                          "              })\n" +
//                                                                          "public final class TestStruct {\n" +
//                                                                          " \n" +
//                                                                          "}");
//        //when
//        final CompileTester compileTester = assert_().about(javaSource())
//                                                     .that(fileObject)
//                                                     .processedWith(new JaccallGenerator());
//        //then
//        compileTester.failsToCompile()
//                     .withErrorContaining("@Struct annotation should be placed on type that extends 'TestStruct_Jaccall_StructType' from package 'org.freedesktop.jaccall.compiletime.linker'")
//                     .in(fileObject);
//    }

    @Test
    public void testStructDuplicateField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("org.freedesktop.libtest.struct.TestStruct",
                                                                          "package org.freedesktop.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.CType;\n" +
                                                                          "import org.freedesktop.jaccall.Field;\n" +
                                                                          "import org.freedesktop.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static org.freedesktop.jaccall.CType.CHAR;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.INT;\n" +
                                                                          "import static org.freedesktop.jaccall.CType.UNSIGNED_SHORT;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {\n" +
                                                                          "             @Field(type = CHAR,\n" +
                                                                          "                    name = \"field0\"),\n" +
                                                                          "             @Field(type = UNSIGNED_SHORT,\n" +
                                                                          "                    name = \"field2\"),\n" +
                                                                          "             @Field(type = INT,\n" +
                                                                          "                    cardinality = 3,\n" +
                                                                          "                    name = \"field2\")\n" +
                                                                          "              })\n" +
                                                                          "public final class TestStruct extends TestStruct_Jaccall_StructType {\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotation has duplicated field name 'field2'.")
                     .in(fileObject);
    }
}