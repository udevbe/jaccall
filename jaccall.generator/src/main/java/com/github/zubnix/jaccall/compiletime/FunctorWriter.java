package com.github.zubnix.jaccall.compiletime;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.PointerFunc;
import com.google.auto.common.SuperficialValidation;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.Generated;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;

final class FunctorWriter {

    private final Messager messager;
    private final Filer    filer;
    private final Elements elementUtils;

    FunctorWriter(final Messager messager,
                  final Filer filer,
                  final Elements elementUtils) {

        this.messager = messager;
        this.filer = filer;
        this.elementUtils = elementUtils;
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
        writeCFunctor(factoryName,
                      cFunctorName,
                      typeElement,
                      executableElement);
        writeJavaFunctor(factoryName,
                         javaFunctorName,
                         typeElement,
                         executableElement);
    }

    private void writeJavaFunctor(final String factoryName,
                                  final String javaFunctorName,
                                  final TypeElement element,
                                  final ExecutableElement executableElement) {

        final AnnotationSpec annotationSpec = AnnotationSpec.builder(Generated.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       JaccallGenerator.class.getName())
                                                            .build();

        final String packageName = this.elementUtils.getPackageOf(element)
                                                    .toString();

        final TypeMirror                returnType      = executableElement.getReturnType();
        final LinkedList<ParameterSpec> $ParameterSpecs = new LinkedList<>();
        final StringBuilder             parameterNames  = new StringBuilder();

        final java.util.List<? extends VariableElement> parameters = executableElement.getParameters();
        for (int i = 0; i < parameters.size(); i++) {
            final VariableElement variableElement = parameters.get(i);
            final String parameterName = variableElement.getSimpleName()
                                                        .toString();
            $ParameterSpecs.add(ParameterSpec.builder(ClassName.get(variableElement.asType()),
                                                      parameterName)
                                             .build());
            if (i != 0) {
                parameterNames.append(", ");
            }
            parameterNames.append(parameterName);
        }

        final CodeBlock $Statement = CodeBlock.builder()
                                              .add(returnType.getKind()
                                                             .equals(TypeKind.VOID) ? "" : "return ")
                                              .add("this.function.$$($L)",
                                                   parameterNames)
                                              .build();
        final MethodSpec $ = MethodSpec.methodBuilder("$")
                                       .addModifiers(Modifier.PUBLIC)
                                       .addAnnotation(Override.class)
                                       .returns(ClassName.get(returnType))
                                       .addParameters($ParameterSpecs)
                                       .addStatement("$L",
                                                     $Statement)
                                       .build();

        final MethodSpec constructor = MethodSpec.constructorBuilder()
                                                 .addParameter(ClassName.get(element),
                                                               "function")
                                                 .addStatement("super($T.ffi_closure(FFI_CIF, function, JNI_METHOD_ID))",
                                                               JNI.class)
                                                 .addStatement("this.function = function")
                                                 .build();

        final FieldSpec jniMethodId = FieldSpec.builder(long.class,
                                                        "JNI_METHOD_ID",
                                                        Modifier.PRIVATE,
                                                        Modifier.STATIC,
                                                        Modifier.FINAL)
                                               .initializer("$T.GetMethodID($T.class, $S, $S)",
                                                            JNI.class,
                                                            ClassName.get(element),
                                                            "$",
                                                            new MethodParser(this.messager).parseJniSignature(executableElement))
                                               .build();

        final TypeSpec typeSpec = TypeSpec.classBuilder(javaFunctorName)
                                          .addAnnotation(annotationSpec)
                                          .addModifiers(Modifier.FINAL)
                                          .superclass(ClassName.get(packageName,
                                                                    factoryName))
                                          .addField(jniMethodId)
                                          .addMethod(constructor)
                                          .addMethod($)
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

    private void writeCFunctor(final String factoryName,
                               final String cFunctorName,
                               final TypeElement element,
                               final ExecutableElement executableElement) {

        final AnnotationSpec annotationSpec = AnnotationSpec.builder(Generated.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       JaccallGenerator.class.getName())
                                                            .build();

        final String packageName = this.elementUtils.getPackageOf(element)
                                                    .toString();

        final MethodSpec constructor = MethodSpec.constructorBuilder()
                                                 .addParameter(long.class,
                                                               "address")
                                                 .addStatement("super(address)")
                                                 .build();

        final TypeMirror                returnType      = executableElement.getReturnType();
        final LinkedList<ParameterSpec> $ParameterSpecs = new LinkedList<>();
        final StringBuilder             parameterNames  = new StringBuilder();

        final java.util.List<? extends VariableElement> parameters = executableElement.getParameters();
        for (int i = 0; i < parameters.size(); i++) {
            final VariableElement variableElement = parameters.get(i);
            final String parameterName = variableElement.getSimpleName()
                                                        .toString();
            $ParameterSpecs.add(ParameterSpec.builder(ClassName.get(variableElement.asType()),
                                                      parameterName)
                                             .build());
            if (i != 0) {
                parameterNames.append(", ");
            }
            parameterNames.append(parameterName);
        }


        final CodeBlock $Statement = CodeBlock.builder()
                                              .add(returnType.getKind()
                                                             .equals(TypeKind.VOID) ? "" : "return ")
                                              .add("_$$(this.address, $L)",
                                                   parameterNames)
                                              .build();
        final MethodSpec $ = MethodSpec.methodBuilder("$")
                                       .addModifiers(Modifier.PUBLIC)
                                       .addAnnotation(Override.class)
                                       .returns(ClassName.get(returnType))
                                       .addParameters($ParameterSpecs)
                                       .addStatement("$L",
                                                     $Statement)
                                       .build();

        final LinkedList<ParameterSpec> _$ParameterSpecs = new LinkedList<>($ParameterSpecs);
        _$ParameterSpecs.addFirst(ParameterSpec.builder(long.class,
                                                        "address")
                                               .build());
        final MethodSpec _$ = MethodSpec.methodBuilder("_$")
                                        .addModifiers(Modifier.PRIVATE,
                                                      Modifier.STATIC,
                                                      Modifier.NATIVE)
                                        .returns(ClassName.get(returnType))
                                        .addParameters(_$ParameterSpecs)
                                        .build();

        final CodeBlock staticBlock = CodeBlock.builder()
                                               .addStatement("$T.linkFuncPtr($T.class,  $S, $L, $S, FFI_CIF)",
                                                             JNI.class,
                                                             ClassName.get(packageName,
                                                                           cFunctorName),
                                                             "_$",
                                                             parameters.size(),
                                                             new MethodParser(this.messager).parseJniSignature(executableElement)
                                                                                            .replace("(",
                                                                                                     "(J"))
                                               .build();

        final TypeSpec typeSpec = TypeSpec.classBuilder(cFunctorName)
                                          .addAnnotation(annotationSpec)
                                          .addModifiers(Modifier.FINAL)
                                          .addStaticBlock(staticBlock)
                                          .superclass(ClassName.get(packageName,
                                                                    factoryName))
                                          .addMethod(constructor)
                                          .addMethod($)
                                          .addMethod(_$)
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

        final String packageName = this.elementUtils.getPackageOf(element)
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
