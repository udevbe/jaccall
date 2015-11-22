package com.github.zubnix.jaccall.compiletime.linker;


import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Lib;
import com.github.zubnix.jaccall.LinkSymbols;
import com.github.zubnix.jaccall.Lng;
import com.github.zubnix.jaccall.Ptr;
import com.github.zubnix.jaccall.Struct;
import com.github.zubnix.jaccall.Unsigned;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.SetMultimap;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class LinkSymbolsWriter implements BasicAnnotationProcessor.ProcessingStep {

    private static final String UNSIGNED = Unsigned.class.getSimpleName();
    private static final String LNG      = Lng.class.getSimpleName();
    private static final String PTR      = Ptr.class.getSimpleName();
    private static final String BY_VAL   = ByVal.class.getSimpleName();
    private static final String STRUCT   = Struct.class.getSimpleName();

    private final LinkerGenerator linkerGenerator;

    LinkSymbolsWriter(final LinkerGenerator linkerGenerator) {
        this.linkerGenerator = linkerGenerator;
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return Collections.singleton(Lib.class);
    }

    @Override
    public void process(final SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        for (final Element element : elementsByAnnotation.values()) {

            final List<String> methodNames = new LinkedList<>();
            final List<Byte> argSizes = new LinkedList<>();
            final LinkedList<Object> statementTypes = new LinkedList<>();
            final List<String> ffiSignatures = new LinkedList<>();
            final List<String> jniSignatures = new LinkedList<>();

            final TypeElement typeElement = (TypeElement) element;
            for (final ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
                //gather link symbol information for each native method
                if (executableElement.getModifiers()
                                     .contains(Modifier.NATIVE)) {
                    parseMethodName(executableElement,
                                    methodNames);
                    parseArgSize(executableElement,
                                 argSizes);
                    //extra jni.class object added for use in $T.ffi_callInterface( later on.
                    statementTypes.add(JNI.class);
                    parseFfiSignature(executableElement,
                                      ffiSignatures,
                                      statementTypes);
                    parseJniSignature(executableElement,
                                      jniSignatures);
                }
            }

            //if no native methods found, return early
            if (methodNames.isEmpty()) {
                return;
            }

            final StringBuilder methodNamesArray = new StringBuilder();
            methodNamesArray.append("new String[]{ /*method name*/\n");
            final StringBuilder argSizesArray = new StringBuilder();
            argSizesArray.append("new byte[]{ /*number of arguments*/\n");
            final StringBuilder ffiSignaturesArray = new StringBuilder();
            ffiSignaturesArray.append("new long[]{ /*FFI call interface*/\n");
            final StringBuilder jniSignaturesArray = new StringBuilder();
            jniSignaturesArray.append("new String[]{ /*JNI method signature*/\n");

            //first element
            String methodName = methodNames.get(0);
            methodNamesArray.append('"')
                            .append(methodName)
                            .append('"');
            argSizesArray.append("/*")
                         .append(methodName)
                         .append("*/ ")
                         .append(argSizes.get(0));
            ffiSignaturesArray.append("/*")
                              .append(methodName)
                              .append("*/ ")
                              .append("$T.ffi_callInterface(")
                              .append(ffiSignatures.get(0))
                              .append(')');
            jniSignaturesArray.append("/*")
                              .append(methodName)
                              .append("*/ ")
                              .append('"')
                              .append(jniSignatures.get(0))
                              .append('"');

            //subsequent elements, with prepended comma
            for (int i = 1, methodNamesSize = methodNames.size(); i < methodNamesSize; i++) {
                methodName = methodNames.get(i);
                methodNamesArray.append(',')
                                .append('\n')
                                .append('"')
                                .append(methodName)
                                .append('"');
                argSizesArray.append(',')
                             .append('\n')
                             .append("/*")
                             .append(methodName)
                             .append("*/ ")
                             .append(argSizes.get(i));
                ffiSignaturesArray.append(',')
                                  .append('\n')
                                  .append("/*")
                                  .append(methodName)
                                  .append("*/ ")
                                  .append("$T.ffi_callInterface(")
                                  .append(ffiSignatures.get(i))
                                  .append(')');
                jniSignaturesArray.append(',')
                                  .append('\n')
                                  .append("/*")
                                  .append(methodName)
                                  .append("*/ ")
                                  .append('"')
                                  .append(jniSignatures.get(i))
                                  .append('"');
            }

            methodNamesArray.append("\n}");
            argSizesArray.append("\n}");
            ffiSignaturesArray.append("\n}");
            jniSignaturesArray.append("\n}");


            final MethodSpec constructor = MethodSpec.constructorBuilder()
                                                     .addModifiers(Modifier.PUBLIC)
                                                     .addStatement("super(" +
                                                                   methodNamesArray.toString() + ',' + '\n'
                                                                   + argSizesArray.toString() + ',' + '\n'
                                                                   + ffiSignaturesArray.toString() + ',' + '\n'
                                                                   + jniSignaturesArray.toString() + ")",
                                                                   statementTypes.toArray())
                                                     .build();


            final TypeSpec typeSpec = TypeSpec.classBuilder(element.getSimpleName() + "_Jaccall_LinkSymbols")
                                              .addModifiers(Modifier.PUBLIC)
                                              .addModifiers(Modifier.FINAL)
                                              .superclass(LinkSymbols.class)
                                              .addMethod(constructor)
                                              .build();

            // 0 if we have a non top level type, or 1 if we do.
            for (final PackageElement packageElement : ElementFilter.packagesIn(Collections.singletonList(element.getEnclosingElement()))) {
                final JavaFile javaFile = JavaFile.builder(packageElement.getQualifiedName()
                                                                         .toString(),
                                                           typeSpec)
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
                                                      element);
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseJniSignature(final ExecutableElement executableElement,
                                   final List<String> jniSignatures) {
        final StringBuilder jniSignature = new StringBuilder();
        jniSignature.append('(');
        for (final VariableElement variableElement : executableElement.getParameters()) {
            jniSignature.append(parseJNIChar(variableElement.asType(),
                                             variableElement));
        }
        jniSignature.append(')');
        jniSignature.append(parseJNIChar(executableElement.getReturnType(),
                                         executableElement));
        jniSignatures.add(jniSignature.toString());
    }

    private char parseJNIChar(final TypeMirror typeMirror,
                              final Element element) {
        final TypeKind kind = typeMirror.getKind();

        switch (kind) {
            case BYTE:
                return 'B';
            case SHORT:
                return 'S';
            case INT:
                return 'I';
            case LONG:
                return 'J';
            case FLOAT:
                return 'F';
            case DOUBLE:
                return 'D';
            case VOID:
                return 'V';
            default:
                this.linkerGenerator.getProcessingEnvironment()
                                    .getMessager()
                                    .printMessage(Diagnostic.Kind.ERROR,
                                                  "Unsupported type " + typeMirror,
                                                  element);
                return 0;
        }
    }


    private void parseFfiSignature(final ExecutableElement executableElement,
                                   final List<String> ffiSignatures,
                                   final List<Object> statementTypes) {
        final StringBuilder ffiSignature = new StringBuilder();

        //return type
        ffiSignature.append(parseFfiString(executableElement.getReturnType(),
                                           executableElement,
                                           statementTypes));

        //arguments
        for (final VariableElement variableElement : executableElement.getParameters()) {
            ffiSignature.append(", ")
                        .append(parseFfiString(variableElement.asType(),
                                               variableElement,
                                               statementTypes));
        }

        ffiSignatures.add(ffiSignature.toString());
    }

    private String parseFfiString(final TypeMirror typeMirror,
                                  final Element element,
                                  final List<Object> statementTypes) {

        Map<? extends ExecutableElement, ? extends AnnotationValue> unsigned = null;
        Map<? extends ExecutableElement, ? extends AnnotationValue> lng      = null;
        Map<? extends ExecutableElement, ? extends AnnotationValue> ptr      = null;
        Map<? extends ExecutableElement, ? extends AnnotationValue> byVal    = null;

        for (final AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            final DeclaredType annotationType = annotationMirror.getAnnotationType();
            final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();

            final String simpleName = annotationType.asElement()
                                                    .getSimpleName()
                                                    .toString();
            if (simpleName.equals(PTR)) {
                ptr = elementValues;
            }
            else if (simpleName.equals(UNSIGNED)) {
                unsigned = elementValues;
            }
            else if (simpleName.equals(LNG)) {
                lng = elementValues;
            }
            else if (simpleName.equals(BY_VAL)) {
                byVal = elementValues;
            }
        }

        final TypeKind kind = typeMirror.getKind();
        switch (kind) {
            case BYTE:
                statementTypes.add(JNI.class);
                return unsigned == null ? "$T.FFI_TYPE_SINT8" : "$T.FFI_TYPE_UINT8";
            case SHORT:
                statementTypes.add(JNI.class);
                return unsigned == null ? "$T.FFI_TYPE_SINT16" : "$T.FFI_TYPE_UINT16";
            case INT:
                statementTypes.add(JNI.class);
                return unsigned == null ? "$T.FFI_TYPE_SINT32" : "$T.FFI_TYPE_UINT32";
            case LONG:
                if (ptr != null) {
                    //it's a pointer
                    statementTypes.add(JNI.class);
                    return "$T.FFI_TYPE_POINTER";
                }
                else if (byVal != null) {
                    //it's a struct by value
                    return parseByVal(byVal,
                                      statementTypes);
                }
                else if (lng != null && unsigned != null) {
                    //it's an unsigned long long
                    statementTypes.add(JNI.class);
                    return "$T.FFI_TYPE_UINT64";
                }
                else if (lng != null) {
                    //it's a signed long long
                    statementTypes.add(JNI.class);
                    return "$T.FFI_TYPE_SINT64";
                }
                else if (unsigned != null) {
                    //it's an unsigned long
                    statementTypes.add(JNI.class);
                    return "$T.FFI_TYPE_ULONG";
                }
                else {
                    //it's an signed long
                    statementTypes.add(JNI.class);
                    return "$T.FFI_TYPE_SLONG";
                }
            case FLOAT:
                statementTypes.add(JNI.class);
                return "$T.FFI_TYPE_FLOAT";
            case DOUBLE:
                statementTypes.add(JNI.class);
                return "$T.FFI_TYPE_DOUBLE";
            case VOID:
                statementTypes.add(JNI.class);
                return "$T.FFI_TYPE_VOID";
            default:
                this.linkerGenerator.getProcessingEnvironment()
                                    .getMessager()
                                    .printMessage(Diagnostic.Kind.ERROR,
                                                  "Unsupported type " + typeMirror,
                                                  element);
                return "";
        }
    }

    private String parseByVal(final Map<? extends ExecutableElement, ? extends AnnotationValue> byVal,
                              final List<Object> statementTypes) {
        final StringBuilder structByVal = new StringBuilder();
        for (final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> annotationEntry : byVal.entrySet()) {
            if (annotationEntry.getKey()
                               .getSimpleName()
                               .toString()
                               .equals("value")) {
                final AnnotationValue value = annotationEntry.getValue();
                final TypeMirror structClass = (TypeMirror) value.getValue();

                parseStructFields(annotationEntry.getKey(),
                                  structByVal,
                                  structClass,
                                  statementTypes);
            }
        }
        return structByVal.toString();
    }

    private void parseStructFields(final Element element,
                                   final StringBuilder structByVal,
                                   final TypeMirror structClass,
                                   final List<Object> statementTypes) {
        final DeclaredType structTypeType    = (DeclaredType) structClass;
        final Element      structTypeElement = structTypeType.asElement();

        final String structTypeName = structTypeElement.getSimpleName()
                                                       .toString();
        if (structTypeName.equals("StructType")) {
            this.linkerGenerator.getProcessingEnvironment()
                                .getMessager()
                                .printMessage(Diagnostic.Kind.ERROR,
                                              "Declared struct type must be a subclass of 'com.github.zubnix.jaccall.StructType'.",
                                              element);
        }

        //TODO check if struct type element has a struct annotation
        //structTypeElement.getAnnotationMirrors()

        final Set<PackageElement> packageElements = ElementFilter.packagesIn(Collections.singleton(structTypeElement.getEnclosingElement()));
        if (packageElements.isEmpty()) {
            this.linkerGenerator.getProcessingEnvironment()
                                .getMessager()
                                .printMessage(Diagnostic.Kind.ERROR,
                                              "Declared struct type must be a top level type.",
                                              element);
        }

        for (final PackageElement packageElement : packageElements) {
            final String packageName = packageElement.getQualifiedName()
                                                     .toString();
            statementTypes.add(ClassName.get(packageName,
                                             structTypeName));
            structByVal.append("$T.FFI_TYPE");
        }
    }

    private void parseArgSize(final ExecutableElement executableElement,
                              final List<Byte> argSizes) {
        argSizes.add((byte) executableElement.getParameters()
                                             .size());
    }

    private void parseMethodName(final ExecutableElement executableElement,
                                 final List<String> methodNames) {
        methodNames.add(executableElement.getSimpleName()
                                         .toString());
    }
}
