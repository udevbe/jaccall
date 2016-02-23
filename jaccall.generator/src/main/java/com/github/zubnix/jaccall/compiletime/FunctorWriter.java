package com.github.zubnix.jaccall.compiletime;


import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.PointerFactory;
import com.github.zubnix.jaccall.PointerFunc;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
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
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
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
            for (final ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
                writeFunctorImplementation(typeElement,
                                           executableElement);
                break;
            }
        }
    }

    private void writeFunctorImplementation(final TypeElement typeElement,
                                            final ExecutableElement executableElement) {

        final String factoryName        = "Pointer" + typeElement.getSimpleName();
        final String cFunctorName       = typeElement.getSimpleName() + "_Jaccall_C";
        final String javaFunctorName    = typeElement.getSimpleName() + "_Jaccall_J";
        final String pointerFactoryName = typeElement.getSimpleName() + "_PointerFactory";


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
        writePointerFactory(factoryName,
                            pointerFactoryName,
                            cFunctorName,
                            typeElement);
    }

    private void writePointerFactory(final String factoryName,
                                     final String pointerFactoryName,
                                     final String cFunctorName,
                                     final TypeElement element) {

        final AnnotationSpec annotationSpec = AnnotationSpec.builder(Generated.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       JaccallGenerator.class.getName())
                                                            .build();

        final String packageName = this.elementUtils.getPackageOf(element)
                                                    .toString();

        final MethodSpec createFunc = MethodSpec.methodBuilder("create")
                                                .addAnnotation(Override.class)
                                                .addModifiers(Modifier.PUBLIC)
                                                .returns(ClassName.get(packageName,
                                                                       factoryName))
                                                .addParameter(Type.class,
                                                              "type")
                                                .addParameter(long.class,
                                                              "address")
                                                .addParameter(ByteBuffer.class,
                                                              "buffer")
                                                .addStatement("return new $T(address, buffer)",
                                                              ClassName.get(packageName,
                                                                            cFunctorName))
                                                .build();

        final TypeSpec typeSpec = TypeSpec.classBuilder(pointerFactoryName)
                                          .addAnnotation(annotationSpec)
                                          .addModifiers(Modifier.PUBLIC,
                                                        Modifier.FINAL)
                                          .addSuperinterface(ParameterizedTypeName.get(ClassName.get(PointerFactory.class),
                                                                                       ClassName.get(packageName,
                                                                                                     factoryName)))
                                          .addMethod(createFunc)
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
                                                 .addStatement("super($T.ffi_closure(FFI_CIF, function, JNI_METHOD_ID), $T.allocate(0))",
                                                               JNI.class,
                                                               ByteBuffer.class)
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
                                          .addField(ClassName.get(element),
                                                    "function",
                                                    Modifier.PRIVATE,
                                                    Modifier.FINAL)
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
                                                 .addParameter(ByteBuffer.class,
                                                               "buffer")
                                                 .addStatement("super(address, buffer)")
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
                                                             parameters.size() + 1,
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
                                                 .addParameter(ByteBuffer.class,
                                                               "buffer")
                                                 .addStatement("super($T.class, address, buffer)",
                                                               ClassName.get(element))
                                                 .build();

        final MethodSpec nref = MethodSpec.methodBuilder("nref")
                                          .addModifiers(Modifier.PUBLIC,
                                                        Modifier.STATIC)
                                          .returns(ParameterizedTypeName.get(ClassName.get(Pointer.class),
                                                                             ClassName.get(element)))
                                          .addParameter(ClassName.get(element),
                                                        "function")
                                          .beginControlFlow("if(function instanceof $T)",
                                                            ClassName.get(packageName,
                                                                          factoryName))
                                          .addStatement("return ($T)function",
                                                        ClassName.get(packageName,
                                                                      factoryName))
                                          .endControlFlow()
                                          .addStatement("return new $T(function)",
                                                        ClassName.get(packageName,
                                                                      javaFunctorName))
                                          .build();

        final TypeSpec typeSpec = TypeSpec.classBuilder(factoryName)
                                          .addAnnotation(annotationSpec)
                                          .addModifiers(Modifier.PUBLIC,
                                                        Modifier.ABSTRACT)
                                          .superclass(ParameterizedTypeName.get(ClassName.get(PointerFunc.class),
                                                                                ClassName.get(element)))
                                          .addSuperinterface(TypeName.get(element.asType()))
                                          .addField(ffiCif)
                                          .addMethod(constructor)
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
