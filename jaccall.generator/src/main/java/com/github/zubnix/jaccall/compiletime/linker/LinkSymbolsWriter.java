package com.github.zubnix.jaccall.compiletime.linker;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.LinkSymbols;
import com.github.zubnix.jaccall.compiletime.MethodParser;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.Generated;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

final class LinkSymbolsWriter {

    private final LinkerGenerator linkerGenerator;

    LinkSymbolsWriter(final LinkerGenerator linkerGenerator) {
        this.linkerGenerator = linkerGenerator;
    }

    public void process(final Set<? extends TypeElement> typeElements) {

        final MethodParser methodParser = new MethodParser(this.linkerGenerator.getProcessingEnvironment());

        for (final TypeElement typeElement : typeElements) {

            final CodeBlock.Builder methodNamesArray = CodeBlock.builder();
            final CodeBlock.Builder argSizesArray = CodeBlock.builder();
            final CodeBlock.Builder ffiSignaturesArray = CodeBlock.builder();
            final CodeBlock.Builder jniSignaturesArray = CodeBlock.builder();

            final List<ExecutableElement> methodsIn = ElementFilter.methodsIn(typeElement.getEnclosedElements());
            for (int i = 0; i < methodsIn.size(); i++) {
                final ExecutableElement executableElement = methodsIn.get(i);
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
                    ffiSignaturesArray.add("/*$L*/ $T.ffi_callInterface($L)",
                                           methodName,
                                           JNI.class,
                                           methodParser.parseFfiSignature(executableElement));
                    jniSignaturesArray.add("/*$L*/ $S",
                                           methodName,
                                           methodParser.parseJniSignature(executableElement));
                }
            }

            final MethodSpec constructor = MethodSpec.constructorBuilder()
                                                     .addModifiers(Modifier.PUBLIC)
                                                     .addStatement("super(new $T{ /*method name*/\n $L\n },\n" +
                                                                   "new $T{ /*number of arguments*/\n $L\n },\n" +
                                                                   "new $T{ /*FFI call interface*/\n $L\n },\n" +
                                                                   "new $T{ /*JNI method signature*/\n $L\n })",
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
                                                                           LinkerGenerator.class.getName())
                                                                .build();
            final TypeSpec typeSpec = TypeSpec.classBuilder(typeElement.getSimpleName() + "_Jaccall_LinkSymbols")
                                              .addAnnotation(annotationSpec)
                                              .addModifiers(Modifier.PUBLIC)
                                              .addModifiers(Modifier.FINAL)
                                              .superclass(LinkSymbols.class)
                                              .addMethod(constructor)
                                              .build();

            for (final PackageElement packageElement : ElementFilter.packagesIn(Collections.singletonList(typeElement.getEnclosingElement()))) {
                final JavaFile javaFile = JavaFile.builder(packageElement.getQualifiedName()
                                                                         .toString(),
                                                           typeSpec)
                                                  .skipJavaLangImports(true)
                                                  .build();
                try {
                    javaFile.writeTo(this.linkerGenerator.getProcessingEnvironment()
                                                         .getFiler());
                }
                catch (final IOException e) {
                    this.linkerGenerator.getProcessingEnvironment()
                                        .getMessager()
                                        .printMessage(Diagnostic.Kind.ERROR,
                                                      "Could not write linksymbols source file: \n" + javaFile.toString(),
                                                      typeElement);
                    e.printStackTrace();
                }
            }
        }
    }
}
