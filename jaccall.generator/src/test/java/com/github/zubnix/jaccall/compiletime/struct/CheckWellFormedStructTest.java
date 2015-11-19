package com.github.zubnix.jaccall.compiletime.struct;


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
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.Upper",
                                                                          "package com.github.zubnix.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.CHAR;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.INT;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.POINTER;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.UNSIGNED_SHORT;\n" +
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
                                                                          "      public class TestStruct extends TestStruct_Jaccall_StructType{\n" +
                                                                          "      \n" +
                                                                          "   }" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotation should be placed on top level class types only.")
                     .in(fileObject);
    }

    @Test
    public void testStructIsNotClass() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStruct",
                                                                          "package com.github.zubnix.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.CHAR;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.INT;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.POINTER;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.UNSIGNED_SHORT;\n" +
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
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotation must be placed on a class type.")
                     .in(fileObject);
    }

    @Test
    public void testStructIsNotPublic() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStruct",
                                                                          "package com.github.zubnix.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.CHAR;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.INT;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.POINTER;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.UNSIGNED_SHORT;\n" +
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
                                                                          "class TestStruct extends TestStruct_Jaccall_StructType {\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotation must be placed on a public type.")
                     .in(fileObject);
    }

    @Test
    public void testStructIsAbstract() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStruct",
                                                                          "package com.github.zubnix.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.CHAR;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.INT;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.POINTER;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.UNSIGNED_SHORT;\n" +
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
                                                                          "public abstract class TestStruct extends TestStruct_Jaccall_StructType {\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotation can not be placed on an abstract type.")
                     .in(fileObject);
    }

    @Test
    public void testStructNoDefaultConstructor() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStruct",
                                                                          "package com.github.zubnix.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.CHAR;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.INT;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.POINTER;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.UNSIGNED_SHORT;\n" +
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
                                                                          "public class TestStruct extends TestStruct_Jaccall_StructType {\n" +
                                                                          " \n" +
                                                                          "     public TestStruct(int foo) {" +
                                                                          "     \n" +
                                                                          "     }\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotated type must contain a public no-arg constructor.")
                     .in(fileObject);
    }

    @Test
    public void testStructNoFields() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStruct",
                                                                          "package com.github.zubnix.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "@Struct(value = {})\n" +
                                                                          "public class TestStruct extends TestStruct_Jaccall_StructType {\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotation must have at least one field.")
                     .in(fileObject);
    }

    @Test
    public void testStructStaticSIZEField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStruct",
                                                                          "package com.github.zubnix.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.CHAR;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.INT;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.POINTER;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.UNSIGNED_SHORT;\n" +
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
                                                                          "public class TestStruct extends TestStruct_Jaccall_StructType {\n" +
                                                                          "\n" +
                                                                          "     public static int SIZE = 1234;\n" +
                                                                          "\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct type may not contain a static field with name SIZE.")
                     .in(fileObject);
    }

    @Test
    public void testStructNotExtending() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStruct",
                                                                          "package com.github.zubnix.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.CHAR;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.INT;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.POINTER;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.UNSIGNED_SHORT;\n" +
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
                                                                          "public class TestStruct {\n" +
                                                                          " \n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotation should be placed on type that extends 'TestStruct_Jaccall_StructType' from package 'com.github.zubnix.jaccall.compiletime.linker'")
                     .in(fileObject);
    }

    @Test
    public void testStructDuplicateField() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("com.github.zubnix.libtest.struct.TestStruct",
                                                                          "package com.github.zubnix.jaccall.compiletime.linker;\n" +
                                                                          "\n" +
                                                                          "import com.github.zubnix.jaccall.CType;\n" +
                                                                          "import com.github.zubnix.jaccall.Field;\n" +
                                                                          "import com.github.zubnix.jaccall.Struct;\n" +
                                                                          "\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.CHAR;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.INT;\n" +
                                                                          "import static com.github.zubnix.jaccall.CType.UNSIGNED_SHORT;\n" +
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
                                                                          "public class TestStruct extends TestStruct_Jaccall_StructType {\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new StructGenerator());
        //then
        compileTester.failsToCompile()
                     .withErrorContaining("@Struct annotation has duplicated field name 'field2'.")
                     .in(fileObject);
    }
}