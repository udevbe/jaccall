package com.github.zubnix.jaccall.compiletime.linker;


import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.Lib;
import com.github.zubnix.jaccall.Lng;
import com.github.zubnix.jaccall.Ptr;
import com.github.zubnix.jaccall.Struct;
import com.github.zubnix.jaccall.Unsigned;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.SetMultimap;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LinkSymbolsWriter implements BasicAnnotationProcessor.ProcessingStep {

    private final LinkerGenerator linkerGenerator;
    private final Elements        elementUtils;
    private final TypeMirror      unsignedType;
    private final TypeMirror      lngType;
    private final TypeMirror      ptrType;
    private final TypeMirror      byValType;
    private final TypeMirror      structType;

    public LinkSymbolsWriter(final LinkerGenerator linkerGenerator) {
        this.linkerGenerator = linkerGenerator;

        elementUtils = linkerGenerator.getProcessingEnvironment()
                                      .getElementUtils();

        unsignedType = elementUtils.getTypeElement(Unsigned.class.getName())
                                   .asType();
        lngType = elementUtils.getTypeElement(Lng.class.getName())
                              .asType();
        ptrType = elementUtils.getTypeElement(Ptr.class.getName())
                              .asType();
        byValType = elementUtils.getTypeElement(ByVal.class.getName())
                                .asType();

        structType = elementUtils.getTypeElement(Struct.class.getName())
                                 .asType();
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return Collections.singleton(Lib.class);
    }

    @Override
    public void process(final SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        for (Element element : elementsByAnnotation.values()) {

            List<String> methodNames = new LinkedList<>();
            List<Byte> argSizes = new LinkedList<>();
            List<String> jaccallSignatures = new LinkedList<>();
            List<String> jniSignatures = new LinkedList<>();

            TypeElement typeElement = (TypeElement) element;
            for (Element enclosedElement : typeElement.getEnclosedElements()) {
                //gather link symbol information for each native method
                if (enclosedElement.getKind()
                                   .equals(ElementKind.METHOD) &&
                    enclosedElement.getModifiers()
                                   .contains(Modifier.NATIVE)) {
                    ExecutableElement executableElement = (ExecutableElement) enclosedElement;

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

            StringBuilder methodNamesArray = new StringBuilder();
            methodNamesArray.append("new String[]{");
            StringBuilder argSizesArray = new StringBuilder();
            argSizesArray.append("new byte[]{");
            StringBuilder jaccallSignaturesArray = new StringBuilder();
            jaccallSignaturesArray.append("new String[]{");
            StringBuilder jniSignaturesArray = new StringBuilder();
            jniSignaturesArray.append("new String[]{");

            methodNamesArray.append(methodNames.get(0));
            argSizesArray.append(argSizes.get(0));
            jaccallSignaturesArray.append(jaccallSignatures.get(0));
            jniSignaturesArray.append(jniSignatures.get(0));

            for (int i = 1, methodNamesSize = methodNames.size(); i < methodNamesSize; i++) {
                methodNamesArray.append(',')
                                .append(methodNames.get(i));
                argSizesArray.append(',')
                             .append(argSizes.get(i));
                jaccallSignaturesArray.append(',')
                                      .append(jaccallSignatures.get(i));
                jniSignaturesArray.append(',')
                                  .append(jniSignatures.get(i));
            }

            StringBuilder superStatement = new StringBuilder();
            superStatement.append("super(")
                          .append(methodNamesArray.toString())
                          .append(',')
                          .append(argSizesArray.toString())
                          .append(',')
                          .append(jaccallSignaturesArray.toString())
                          .append(',')
                          .append(jniSignaturesArray.toString())
                          .append(")");


            final MethodSpec constructor = MethodSpec.constructorBuilder()
                                                     .addModifiers(Modifier.PUBLIC)
                                                     .addStatement(superStatement.toString())
                                                     .build();


            //TODO create new source file
            //TODO write gathered link symbol information to new source file
            //TODO flush source file
        }
    }

    private void parseJniSignature(final ExecutableElement executableElement,
                                   final List<String> jniSignatures) {
        StringBuilder jniSignature = new StringBuilder();
        jniSignature.append('(');
        for (VariableElement variableElement : executableElement.getParameters()) {
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
                linkerGenerator.getProcessingEnvironment()
                               .getMessager()
                               .printMessage(Diagnostic.Kind.ERROR,
                                             "Unsupported type " + typeMirror,
                                             element);
                return 0;
        }
    }


    private void parseJaccallSignature(final ExecutableElement executableElement,
                                       final List<String> jaccallSignatures) {
        StringBuilder jaccallSignature = new StringBuilder();
        for (VariableElement variableElement : executableElement.getParameters()) {
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

        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            final DeclaredType annotationType = annotationMirror.getAnnotationType();
            final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();

            if (annotationType.equals(this.ptrType)) {
                ptr = elementValues;
            }
            else if (annotationType.equals(this.unsignedType)) {
                unsigned = elementValues;
            }
            else if (annotationType.equals(this.lngType)) {
                lng = elementValues;
            }
            else if (annotationType.equals(this.byValType)) {
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
                    //return parseByVal(byVal);
                    return "";
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
                linkerGenerator.getProcessingEnvironment()
                               .getMessager()
                               .printMessage(Diagnostic.Kind.ERROR,
                                             "Unsupported type " + typeMirror,
                                             element);
                return "";
        }
    }

    private String parseByVal(final Map<? extends ExecutableElement, ? extends AnnotationValue> byVal) {
        StringBuilder structByVal = new StringBuilder();
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> annotationEntry : byVal.entrySet()) {
            if (annotationEntry.getKey()
                               .getSimpleName()
                               .toString()
                               .equals("value")) {
                final TypeMirror structClass = (TypeMirror) annotationEntry.getValue();
                parseStructFields(annotationEntry.getKey(),
                                  structByVal,
                                  structClass);
            }
        }
        return structByVal.toString();
    }

    private void parseStructFields(final Element element,
                                   StringBuilder structByVal,
                                   final TypeMirror structClass) {
        structByVal.append('t');

        //TODO use annotation mirrors instead
        for (AnnotationMirror annotationMirror : structClass.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType()
                                .equals(this.structType)) {
                final Map<? extends ExecutableElement, ? extends AnnotationValue> structValues = annotationMirror.getElementValues();


            }
        }

//
//        final Struct structClassAnnotation = structClass.getAnnotation(Struct.class);
//        for (Field field : structClassAnnotation.value()) {
//            final int cardinality = field.cardinality();
//            if (cardinality < 1) {
//                linkerGenerator.getProcessingEnvironment()
//                               .getMessager()
//                               .printMessage(Diagnostic.Kind.ERROR,
//                                             "Cardinality of struct field must be at least 1.",
//                                             element);
//            }
//
//            final CType type = field.type();
//
//            for (int i = 0; i < cardinality; i++) {
//                if (type.equals(CType.STRUCT)) {
//                    final TypeElement typeElement = elementUtils.getTypeElement(field.dataType()
//                                                                                     .getName());
//                    final TypeMirror structTypeMirror = typeElement.asType();
//                    //parse embedded struct
//                    parseStructFields(typeElement,
//                                      structByVal,
//                                      structTypeMirror);
//                }
//                else {
//                    structByVal.append(type.getSignature());
//                }
//            }
//        }
//        structByVal.append(']');
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
