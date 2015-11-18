package com.github.zubnix.jaccall.compiletime.linker;


import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.CType;
import com.github.zubnix.jaccall.Lib;
import com.github.zubnix.jaccall.LinkSymbols;
import com.github.zubnix.jaccall.Lng;
import com.github.zubnix.jaccall.Ptr;
import com.github.zubnix.jaccall.Struct;
import com.github.zubnix.jaccall.Unsigned;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.SetMultimap;
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
            final List<String> jaccallSignatures = new LinkedList<>();
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
                    parseJaccallSignature(executableElement,
                                          jaccallSignatures);
                    parseJniSignature(executableElement,
                                      jniSignatures);
                }
            }

            //if no native methods found, return early
            if (methodNames.isEmpty()) {
                return;
            }

            final StringBuilder methodNamesArray = new StringBuilder();
            methodNamesArray.append("new String[]{");
            final StringBuilder argSizesArray = new StringBuilder();
            argSizesArray.append("new byte[]{");
            final StringBuilder jaccallSignaturesArray = new StringBuilder();
            jaccallSignaturesArray.append("new String[]{");
            final StringBuilder jniSignaturesArray = new StringBuilder();
            jniSignaturesArray.append("new String[]{");

            methodNamesArray.append('"')
                            .append(methodNames.get(0))
                            .append('"');
            argSizesArray.append(argSizes.get(0));
            jaccallSignaturesArray.append('"')
                                  .append(jaccallSignatures.get(0))
                                  .append('"');
            jniSignaturesArray.append('"')
                              .append(jniSignatures.get(0))
                              .append('"');

            for (int i = 1, methodNamesSize = methodNames.size(); i < methodNamesSize; i++) {
                methodNamesArray.append(',')
                                .append('"')
                                .append(methodNames.get(i))
                                .append('"');
                argSizesArray.append(',')
                             .append(argSizes.get(i));
                jaccallSignaturesArray.append(',')
                                      .append('"')
                                      .append(jaccallSignatures.get(i))
                                      .append('"');
                jniSignaturesArray.append(',')
                                  .append('"')
                                  .append(jniSignatures.get(i))
                                  .append('"');
            }

            methodNamesArray.append('}');
            argSizesArray.append('}');
            jaccallSignaturesArray.append('}');
            jniSignaturesArray.append('}');


            final MethodSpec constructor = MethodSpec.constructorBuilder()
                                                     .addModifiers(Modifier.PUBLIC)
                                                     .addStatement("super(" +
                                                                   methodNamesArray.toString() + ','
                                                                   + argSizesArray.toString() + ','
                                                                   + jaccallSignaturesArray.toString() + ','
                                                                   + jniSignaturesArray.toString() + ")")
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


    private void parseJaccallSignature(final ExecutableElement executableElement,
                                       final List<String> jaccallSignatures) {
        final StringBuilder jaccallSignature = new StringBuilder();
        for (final VariableElement variableElement : executableElement.getParameters()) {
            jaccallSignature.append(parseJaccallString(variableElement.asType(),
                                                       variableElement));
        }
        jaccallSignature.append(parseJaccallString(executableElement.getReturnType(),
                                                   executableElement));
        jaccallSignatures.add(jaccallSignature.toString());
    }

    private String parseJaccallString(final TypeMirror typeMirror,
                                      final Element element) {

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
                return unsigned == null ? "c" : "C";
            case SHORT:
                return unsigned == null ? "s" : "S";
            case INT:
                return unsigned == null ? "i" : "I";
            case LONG:
                if (ptr != null) {
                    //it's a pointer
                    return "p";
                }
                else if (byVal != null) {
                    //it's a struct by value
                    return parseByVal(byVal);
                }
                else if (lng != null && unsigned != null) {
                    //it's an unsigned long long
                    return "L";
                }
                else if (lng != null) {
                    //it's a signed long long
                    return "l";
                }
                else if (unsigned != null) {
                    //it's an unsigned long
                    return "J";
                }
                else {
                    //it's an signed long
                    return "j";
                }
            case FLOAT:
                return "f";
            case DOUBLE:
                return "d";
            case VOID:
                return "v";
            default:
                this.linkerGenerator.getProcessingEnvironment()
                                    .getMessager()
                                    .printMessage(Diagnostic.Kind.ERROR,
                                                  "Unsupported type " + typeMirror,
                                                  element);
                return "";
        }
    }

    private String parseByVal(final Map<? extends ExecutableElement, ? extends AnnotationValue> byVal) {
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
                                  structClass);
            }
        }
        return structByVal.toString();
    }

    private void parseStructFields(final Element element,
                                   final StringBuilder structByVal,
                                   final TypeMirror structClass) {
        final DeclaredType structTypeType = (DeclaredType) structClass;
        if (structTypeType.asElement()
                          .getSimpleName()
                          .toString()
                          .equals("StructType")) {
            this.linkerGenerator.getProcessingEnvironment()
                                .getMessager()
                                .printMessage(Diagnostic.Kind.ERROR,
                                              "Declared struct type must be a subclass of 'com.github.zubnix.jaccall.StructType'.",
                                              element);
        }

        for (final AnnotationMirror annotationMirror : structTypeType.asElement()
                                                                     .getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType()
                                .asElement()
                                .getSimpleName()
                                .toString()
                                .equals(STRUCT)) {

                Boolean union = false;
                for (final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> structAttribute : annotationMirror.getElementValues()
                                                                                                                               .entrySet()) {
                    if (structAttribute.getKey()
                                       .getSimpleName()
                                       .toString()
                                       .equals("union")) {
                        union = (Boolean) structAttribute.getValue()
                                                         .getValue();
                        break;
                    }
                }

                if (union) {
                    structByVal.append('u');
                }
                else {
                    structByVal.append('t');
                }

                for (final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> structAttribute : annotationMirror.getElementValues()
                                                                                                                               .entrySet()) {
                    if (structAttribute.getKey()
                                       .getSimpleName()
                                       .toString()
                                       .equals("value")) {
                        final List<? extends AnnotationValue> fieldAnnotations = (List<? extends AnnotationValue>) structAttribute.getValue()
                                                                                                                                  .getValue();
                        if (fieldAnnotations.isEmpty()) {
                            this.linkerGenerator.getProcessingEnvironment()
                                                .getMessager()
                                                .printMessage(Diagnostic.Kind.ERROR,
                                                              "Emptry struct not allowed.",
                                                              structAttribute.getKey());
                        }

                        parseFieldAnnotations(structByVal,
                                              fieldAnnotations);
                    }
                }

                structByVal.append(']');
            }
        }
    }

    private void parseFieldAnnotations(final StringBuilder structByVal,
                                       final List<? extends AnnotationValue> fieldAnnotations) {
        for (final AnnotationValue fieldAnnotation : fieldAnnotations) {
            final AnnotationMirror fieldAnnotationMirror = (AnnotationMirror) fieldAnnotation.getValue();

            VariableElement cType = null;
            Integer cardinality = 1;
            TypeMirror dataType = null;

            for (final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> fieldAttribute : fieldAnnotationMirror.getElementValues()
                                                                                                                               .entrySet()) {
                if (fieldAttribute.getKey()
                                  .getSimpleName()
                                  .toString()
                                  .equals("type")) {

                    cType = (VariableElement) fieldAttribute.getValue()
                                                            .getValue();
                }
                else if (fieldAttribute.getKey()
                                       .getSimpleName()
                                       .toString()
                                       .equals("cardinality")) {
                    cardinality = (Integer) fieldAttribute.getValue()
                                                          .getValue();
                    if (cardinality < 1) {
                        this.linkerGenerator.getProcessingEnvironment()
                                            .getMessager()
                                            .printMessage(Diagnostic.Kind.ERROR,
                                                          "Cardinality of struct field must be at least 1.",
                                                          fieldAttribute.getKey());
                    }
                }
                else if (fieldAttribute.getKey()
                                       .getSimpleName()
                                       .toString()
                                       .equals("dataType")) {
                    dataType = (TypeMirror) fieldAttribute.getValue()
                                                          .getValue();
                }
            }

            for (int i = 0; i < cardinality; i++) {
                parseFieldAnnotation(structByVal,
                                     cType,
                                     dataType);
            }
        }
    }

    private void parseFieldAnnotation(final StringBuilder structByVal,
                                      final VariableElement cType,
                                      final TypeMirror dataType) {

        final char signature = CType.valueOf(cType.getSimpleName()
                                                  .toString())
                                    .getSignature();
        if (signature == 't') {
            if (dataType == null) {
                this.linkerGenerator.getProcessingEnvironment()
                                    .getMessager()
                                    .printMessage(Diagnostic.Kind.ERROR,
                                                  "Data type of struct must be specified.",
                                                  cType);
            }

            parseStructFields(cType,
                              structByVal,
                              dataType);
        }
        else {
            structByVal.append(signature);
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
