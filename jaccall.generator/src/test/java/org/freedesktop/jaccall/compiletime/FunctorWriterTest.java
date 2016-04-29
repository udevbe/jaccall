package org.freedesktop.jaccall.compiletime;

import com.google.testing.compile.CompileTester;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class FunctorWriterTest {

    @Test
    public void testCharFunctor() {
        //given
        final JavaFileObject fileObject = JavaFileObjects.forSourceString("CharFunc",
                                                                          "package org.freedesktop.libtest;\n" +
                                                                          "\n" +
                                                                          "import org.freedesktop.jaccall.Functor;" +
                                                                          "\n" +
                                                                          "@Functor\n" +
                                                                          "public interface CharFunc {\n" +
                                                                          "    byte $(byte value);\n" +
                                                                          "}");
        //when
        final CompileTester compileTester = assert_().about(javaSource())
                                                     .that(fileObject)
                                                     .processedWith(new JaccallGenerator());
        //then
        compileTester.compilesWithoutError()
                     .and()
                     .generatesSources(JavaFileObjects.forSourceString("PointerCharFunc",
                                                                       "package org.freedesktop.libtest;\n" +
                                                                               "\n" +
                                                                               "import javax.annotation.Generated;\n" +
                                                                               "import org.freedesktop.jaccall.JNI;\n" +
                                                                               "import org.freedesktop.jaccall.Pointer;\n" +
                                                                               "import org.freedesktop.jaccall.PointerFunc;\n" +
                                                                               "\n" +
                                                                               "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                               "public abstract class PointerCharFunc extends PointerFunc<CharFunc> implements CharFunc {\n" +
                                                                               "  static final long FFI_CIF = JNI.ffi_callInterface(JNI.FFI_TYPE_SINT8, JNI.FFI_TYPE_SINT8);\n" +
                                                                               "\n" +
                                                                               "  PointerCharFunc(long address) {\n" +
                                                                               "    super(CharFunc.class, address);\n" +
                                                                               "  }\n" +
                                                                               "\n" +
                                                                               "  public static Pointer<CharFunc> nref(CharFunc function) {\n" +
                                                                               "    if(function instanceof PointerCharFunc) {\n" +
                                                                               "      return (PointerCharFunc)function;\n" +
                                                                               "    }\n" +
                                                                               "    return new CharFunc_Jaccall_J(function);\n" +
                                                                               "  }\n" +
                                                                               "}"),
                                       JavaFileObjects.forSourceString("org.freedesktop.libtest.CharFunc_Jaccall_C",
                                                                       "package org.freedesktop.libtest;\n" +
                                                                               "\n" +
                                                                               "import javax.annotation.Generated;\n" +
                                                                               "import org.freedesktop.jaccall.JNI;\n" +
                                                                               "\n" +
                                                                               "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                               "final class CharFunc_Jaccall_C extends PointerCharFunc {\n" +
                                                                               "  static {\n" +
                                                                               "    JNI.linkFuncPtr(CharFunc_Jaccall_C.class,  \"_$\", 2, \"(JB)B\", FFI_CIF);\n" +
                                                                               "  }\n" +
                                                                               "\n" +
                                                                               "  CharFunc_Jaccall_C(long address) {\n" +
                                                                               "    super(address);\n" +
                                                                               "  }\n" +
                                                                               "\n" +
                                                                               "  @Override\n" +
                                                                               "  public byte $(byte value) {\n" +
                                                                               "    return _$(this.address, value);\n" +
                                                                               "  }\n" +
                                                                               "\n" +
                                                                               "  private static native byte _$(long address, byte value);\n" +
                                                                               "}"),
                                       JavaFileObjects.forSourceString("org.freedesktop.libtest.CharFunc_Jaccall_J",
                                                                       "package org.freedesktop.libtest;\n" +
                                                                               "\n" +
                                                                               "import javax.annotation.Generated;\n" +
                                                                               "import org.freedesktop.jaccall.JNI;\n" +
                                                                               "\n" +
                                                                               "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                               "final class CharFunc_Jaccall_J extends PointerCharFunc {\n" +
                                                                               "  private static final long JNI_METHOD_ID = JNI.GetMethodID(CharFunc.class, \"$\", \"(B)B\");\n" +
                                                                               "\n" +
                                                                               "  private final CharFunc function;\n" +
                                                                               "\n" +
                                                                               "  CharFunc_Jaccall_J(CharFunc function) {\n" +
                                                                               "    super(JNI.ffi_closure(FFI_CIF, function, JNI_METHOD_ID));\n" +
                                                                               "    this.function = function;\n" +
                                                                               "  }\n" +
                                                                               "\n" +
                                                                               "  @Override\n" +
                                                                               "  public byte $(byte value) {\n" +
                                                                               "    return this.function.$(value);\n" +
                                                                               "  }\n" +
                                                                               "}"),
                                       JavaFileObjects.forSourceString("CharFunc_PointerFactory",
                                                                       "package org.freedesktop.libtest;\n" +
                                                                               "\n" +
                                                                               "import java.lang.reflect.Type;\n" +
                                                                               "import javax.annotation.Generated;\n" +
                                                                               "import org.freedesktop.jaccall.PointerFactory;\n" +
                                                                               "\n" +
                                                                               "@Generated(\"org.freedesktop.jaccall.compiletime.JaccallGenerator\")\n" +
                                                                               "public final class CharFunc_PointerFactory implements PointerFactory<PointerCharFunc> {\n" +
                                                                               "  @Override\n" +
                                                                               "  public PointerCharFunc create(Type type, long address, boolean autoFree) {\n" +
                                                                               "    return new CharFunc_Jaccall_C(address);\n" +
                                                                               "  }\n" +
                                                                               "}"));
    }

    //TODO test all data types
}