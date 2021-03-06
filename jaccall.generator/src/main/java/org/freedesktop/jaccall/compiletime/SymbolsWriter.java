package org.freedesktop.jaccall.compiletime;


import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Symbol;
import org.freedesktop.jaccall.Symbols;

import javax.annotation.Generated;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

final class SymbolsWriter {

    private final Messager messager;
    private final Filer    filer;

    public SymbolsWriter(final Messager messager,
                         final Filer filer) {
        this.messager = messager;
        this.filer = filer;
    }

    public void process(final TypeElement typeElement) {

        final MethodParser methodParser = new MethodParser(this.messager);

        final CodeBlock.Builder methodNamesArray   = CodeBlock.builder();
        final CodeBlock.Builder argSizesArray      = CodeBlock.builder();
        final CodeBlock.Builder ffiSignaturesArray = CodeBlock.builder();
        final CodeBlock.Builder jniSignaturesArray = CodeBlock.builder();

        final List<ExecutableElement> methodsIn = ElementFilter.methodsIn(typeElement.getEnclosedElements());

        int i = 0;
        for (final ExecutableElement executableElement : methodsIn) {
            if (executableElement.getModifiers()
                                 .contains(Modifier.NATIVE)) {
                if (i != 0) {
                    methodNamesArray.add(",\n");
                    argSizesArray.add(",\n");
                    ffiSignaturesArray.add(",\n");
                    jniSignaturesArray.add(",\n");
                }

                final String methodName = methodParser.parseMethodName(executableElement);
                methodNamesArray.add("$S",
                                     methodName);
                argSizesArray.add("/*$L*/ $L",
                                  methodName,
                                  (byte) methodParser.parseArgSize(executableElement));

                boolean isSymobl = false;
                for (final AnnotationMirror annotationMirror : executableElement.getAnnotationMirrors()) {
                    isSymobl = annotationMirror.getAnnotationType()
                                               .asElement()
                                               .getSimpleName()
                                               .toString()
                                               .equals(Symbol.class.getSimpleName());
                    if (isSymobl) {
                        break;
                    }
                }

                if (isSymobl) {
                    //symbol
                    ffiSignaturesArray.add("/*$L*/ 0",
                                           methodName);
                }
                else {
                    //function
                    ffiSignaturesArray.add("/*$L*/ $T.ffi_callInterface($L)",
                                           methodName,
                                           JNI.class,
                                           methodParser.parseFfiSignature(executableElement));
                }


                jniSignaturesArray.add("/*$L*/ $S",
                                       methodName,
                                       methodParser.parseJniSignature(executableElement));
                i++;
            }
        }

        final MethodSpec constructor = MethodSpec.constructorBuilder()
                                                 .addModifiers(Modifier.PUBLIC)
                                                 .addStatement("super($T.class,\n" +
                                                               "new $T{ /*method name*/\n $L\n },\n" +
                                                               "new $T{ /*number of arguments*/\n $L\n },\n" +
                                                               "new $T{ /*FFI call interface*/\n $L\n },\n" +
                                                               "new $T{ /*JNI method signature*/\n $L\n })",
                                                               ClassName.get(typeElement),
                                                               ArrayTypeName.of(String.class),
                                                               methodNamesArray.build(),
                                                               ArrayTypeName.of(byte.class),
                                                               argSizesArray.build(),
                                                               ArrayTypeName.of(long.class),
                                                               ffiSignaturesArray.build(),
                                                               ArrayTypeName.of(String.class),
                                                               jniSignaturesArray.build())
                                                 .build();

        final AnnotationSpec annotationSpec = AnnotationSpec.builder(Generated.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       JaccallGenerator.class.getName())
                                                            .build();
        final TypeSpec typeSpec = TypeSpec.classBuilder(typeElement.getSimpleName() + "_Symbols")
                                          .addAnnotation(annotationSpec)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addModifiers(Modifier.FINAL)
                                          .superclass(Symbols.class)
                                          .addMethod(constructor)
                                          .build();

        for (final PackageElement packageElement : ElementFilter.packagesIn(Collections.singletonList(typeElement.getEnclosingElement()))) {
            final JavaFile javaFile = JavaFile.builder(packageElement.getQualifiedName()
                                                                     .toString(),
                                                       typeSpec)
                                              .skipJavaLangImports(true)
                                              .build();
            try {
                javaFile.writeTo(this.filer);
            }
            catch (final IOException e) {
                this.messager.printMessage(Diagnostic.Kind.ERROR,
                                           "Could not set symbols source file: \n" + javaFile.toString(),
                                           typeElement);
                e.printStackTrace();
            }
        }
    }
}
