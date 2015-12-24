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
                                                                          "      }" +
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
}