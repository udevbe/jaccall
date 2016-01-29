package com.github.zubnix.jaccall.compiletime;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;
import com.google.auto.common.SuperficialValidation;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.Generated;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
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

    private final Messager messager;
    private final Filer    filer;

    FunctorWriter(final Messager messager,
                  final Filer filer) {

        this.messager = messager;
        this.filer = filer;
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
                this.messager.printMessage(Diagnostic.Kind.ERROR,
                                           "Could not resolve all required compile time type information.",
                                           typeElement);
            }
        }
    }

    private void writeFunctorImplementation(final TypeElement typeElement,
                                            final ExecutableElement executableElement) {

        final String factoryName     = "Pointer" + typeElement.getSimpleName();
        final String cFunctorName    = typeElement.getSimpleName() + "_Jaccall_C";
        final String javaFunctorName = typeElement.getSimpleName() + "_Jaccall_J";

        writeFactory(factoryName,
                     cFunctorName,
                     javaFunctorName,
                     typeElement,
                     executableElement);
        writeCFunctor(cFunctorName,
                      typeElement,
                      executableElement);
        writeJavaFunctor(javaFunctorName,
                         typeElement,
                         executableElement);
    }

    private void writeJavaFunctor(final String javaFunctorName,
                                  final TypeElement functorElement,
                                  final ExecutableElement executableElement) {

    }

    private void writeCFunctor(final String cFunctorName,
                               final TypeElement functorElement,
                               final ExecutableElement executableElement) {

    }

    private void writeFactory(final String factoryName,
                              final String cFunctorName,
                              final String javaFunctorName,
                              final TypeElement element,
                              final ExecutableElement executableElement) {

        final AnnotationSpec annotationSpec = AnnotationSpec.builder(Generated.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       JaccallGenerator.class.getName())
                                                            .build();

        for (final PackageElement packageElement : ElementFilter.packagesIn(Collections.singletonList(element.getEnclosingElement()))) {

            final String packageName = packageElement.getQualifiedName()
                                                     .toString();

            final FieldSpec ffiCif = FieldSpec.builder(long.class,
                                                       "FFI_CIF",
                                                       Modifier.STATIC,
                                                       Modifier.FINAL)
                                              .initializer("$T.ffi_callInterface($L)",
                                                           JNI.class,
                                                           new MethodParser(this.messager).parseFfiSignature(executableElement))
                                              .build();

            final MethodSpec constructor = MethodSpec.constructorBuilder()
                                                     .addParameter(long.class,
                                                                   "address")
                                                     .addStatement("super($T.class, address)",
                                                                   ClassName.get(packageName,
                                                                                 factoryName))
                                                     .build();

            final MethodSpec wrapFunc = MethodSpec.methodBuilder("wrapFunc")
                                                  .addModifiers(Modifier.PUBLIC,
                                                                Modifier.STATIC)
                                                  .addParameter(long.class,
                                                                "address")
                                                  .addStatement("return new $T(address)",
                                                                ClassName.get(packageName,
                                                                              cFunctorName))
                                                  .build();

            final MethodSpec nref = MethodSpec.methodBuilder("nref")
                                              .addModifiers(Modifier.PUBLIC,
                                                            Modifier.STATIC)
                                              .addParameter(ClassName.get(element),
                                                            "function")
                                              .beginControlFlow("if(function instanceof $T)",
                                                                ClassName.get(packageName,
                                                                              factoryName))
                                              .addStatement("return ($T)function",
                                                            ClassName.get(packageName,
                                                                          factoryName))
                                              .endControlFlow()
                                              .addStatement("new $T(function)",
                                                            ClassName.get(packageName,
                                                                          javaFunctorName))
                                              .build();

            final TypeSpec typeSpec = TypeSpec.classBuilder(factoryName)
                                              .addAnnotation(annotationSpec)
                                              .addModifiers(Modifier.PUBLIC,
                                                            Modifier.ABSTRACT)
                                              .superclass(PointerFunc.class)
                                              .addSuperinterface(TypeName.get(element.asType()))
                                              .addField(ffiCif)
                                              .addMethod(constructor)
                                              .addMethod(wrapFunc)
                                              .addMethod(nref)
                                              .build();

            final JavaFile javaFile = JavaFile.builder(packageName,
                                                       typeSpec)
                                              .skipJavaLangImports(true)
                                              .build();
            try {
                javaFile.writeTo(this.filer);
            }
            catch (final IOException e) {
                this.messager.printMessage(Diagnostic.Kind.ERROR,
                                           "Could not write linksymbols source file: \n" + javaFile.toString(),
                                           element);
                e.printStackTrace();
            }
        }
    }
}
