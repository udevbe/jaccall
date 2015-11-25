package com.github.zubnix.jaccall.compiletime.functor;


import com.github.zubnix.jaccall.PointerFunc;
import com.google.auto.common.SuperficialValidation;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
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
import java.util.Set;

final class FunctorWriter {

    private final FunctorGenerator functorGenerator;

    FunctorWriter(final FunctorGenerator functorGenerator) {

        this.functorGenerator = functorGenerator;
    }

    public void process(final Set<? extends TypeElement> typeElements) {
        for (final TypeElement typeElement : typeElements) {
            if (SuperficialValidation.validateElement(typeElement)) {
                for (final ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
                    writeFunctorImplementation(typeElement,
                                               executableElement);
                    break;
                }
            }
            else {
                this.functorGenerator.getProcessingEnvironment()
                                     .getMessager()
                                     .printMessage(Diagnostic.Kind.ERROR,
                                                   "Could not resolve all required compile time type information.",
                                                   typeElement);
            }
        }
    }

    private void writeFunctorImplementation(final TypeElement functorElment,
                                            final ExecutableElement executableElement) {
        writeFactory(functorElment,
                     executableElement);
        writeCFunctor(functorElment,
                      executableElement);
        writeJavaFunctor(functorElment,
                         executableElement);
    }

    private void writeJavaFunctor(final TypeElement functorElement,
                                  final ExecutableElement executableElement) {

    }

    private void writeCFunctor(final TypeElement functorElement,
                               final ExecutableElement executableElement) {

    }

    private void writeFactory(final TypeElement element,
                              final ExecutableElement executableElement) {

        final AnnotationSpec annotationSpec = AnnotationSpec.builder(Generated.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       FunctorGenerator.class.getName())
                                                            .build();

        final TypeSpec typeSpec = TypeSpec.classBuilder("Pointer" + element.getSimpleName())
                                          .addAnnotation(annotationSpec)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addModifiers(Modifier.FINAL)
                                          .superclass(PointerFunc.class)
                .addSuperinterface(TypeName.get(element.asType()))
                        //TODO   .addMethod(constructor)
                .build();

        for (final PackageElement packageElement : ElementFilter.packagesIn(Collections.singletonList(element.getEnclosingElement()))) {
            final JavaFile javaFile = JavaFile.builder(packageElement.getQualifiedName()
                                                                     .toString(),
                                                       typeSpec)
                                              .skipJavaLangImports(true)
                                              .build();
            try {
                javaFile.writeTo(this.functorGenerator.getProcessingEnvironment()
                                                      .getFiler());
            }
            catch (final IOException e) {
                this.functorGenerator.getProcessingEnvironment()
                                     .getMessager()
                                     .printMessage(Diagnostic.Kind.ERROR,
                                                   "Could not write linksymbols source file: \n" + javaFile.toString(),
                                                   element);
                e.printStackTrace();
            }
        }
    }
}
