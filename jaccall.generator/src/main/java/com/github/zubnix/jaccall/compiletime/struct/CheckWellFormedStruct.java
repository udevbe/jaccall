package com.github.zubnix.jaccall.compiletime.struct;

import com.github.zubnix.jaccall.Struct;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.SetMultimap;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CheckWellFormedStruct implements BasicAnnotationProcessor.ProcessingStep {

    private final StructGenerator structGenerator;

    CheckWellFormedStruct(final StructGenerator structGenerator) {

        this.structGenerator = structGenerator;
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return Collections.singleton(Struct.class);
    }

    @Override
    public void process(final SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        for (final TypeElement typeElement : ElementFilter.typesIn(elementsByAnnotation.values())) {
            isTopLevel(typeElement);
            isClass(typeElement);
            isPublic(typeElement);
            isNotAbstract(typeElement);
            hasDefaultConstructor(typeElement);
            hasNonEmptyStructAnnotation(typeElement);
            doesNotHaveStaticSIZEField(typeElement);
            extendsGeneratedStructType(typeElement);
        }
    }

    private void extendsGeneratedStructType(final TypeElement typeElement) {
        final DeclaredType declaredType = (DeclaredType) typeElement.getSuperclass();

    }

    private void isTopLevel(final TypeElement typeElement) {
        if (typeElement.getNestingKind()
                       .isNested()) {
            this.structGenerator.getProcessingEnvironment()
                                .getMessager()
                                .printMessage(Diagnostic.Kind.ERROR,
                                              "@Struct annotation should be placed on top level class types only.",
                                              typeElement);
        }
    }

    private void doesNotHaveStaticSIZEField(final TypeElement typeElement) {
        for (final VariableElement variableElement : ElementFilter.fieldsIn(typeElement.getEnclosedElements())) {
            if (variableElement.getModifiers()
                               .contains(Modifier.STATIC) && variableElement.getSimpleName()
                                                                            .toString()
                                                                            .equals("SIZE")) {
                this.structGenerator.getProcessingEnvironment()
                                    .getMessager()
                                    .printMessage(Diagnostic.Kind.ERROR,
                                                  "@Struct type may not contain a static field with name SIZE.",
                                                  typeElement);
            }
        }
    }

    private void hasNonEmptyStructAnnotation(final TypeElement typeElement) {
        for (final AnnotationMirror annotationMirror : typeElement.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType()
                                .asElement()
                                .getSimpleName()
                                .toString()
                                .equals(Struct.class.getSimpleName())) {
                for (final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues()
                                                                                                                     .entrySet()) {
                    if (entry.getKey()
                             .getSimpleName()
                             .equals("value")) {
                        final List<? extends AnnotationValue> values = (List<? extends AnnotationValue>) entry.getValue()
                                                                                                              .getValue();
                        if (values.isEmpty()) {
                            this.structGenerator.getProcessingEnvironment()
                                                .getMessager()
                                                .printMessage(Diagnostic.Kind.ERROR,
                                                              "@Struct annotation must have at least one field.",
                                                              entry.getKey());
                        }
                    }
                }
            }
        }
    }

    private void isNotAbstract(final TypeElement typeElement) {
        if (typeElement.getModifiers()
                       .contains(Modifier.ABSTRACT)) {
            this.structGenerator.getProcessingEnvironment()
                                .getMessager()
                                .printMessage(Diagnostic.Kind.ERROR,
                                              "@Struct annotation can not be placed on an abstract type.",
                                              typeElement);
        }
    }

    private void hasDefaultConstructor(final TypeElement typeElement) {

        final List<ExecutableElement> executableElements = ElementFilter.constructorsIn(typeElement.getEnclosedElements());
        if (executableElements.isEmpty()) {
            //no constructor specified in source, java will provide a default public one
            return;
        }

        for (final ExecutableElement constructorElement : executableElements) {
            if (constructorElement.getParameters()
                                  .size() == 0 && constructorElement.getModifiers()
                                                                    .contains(Modifier.PUBLIC)) {
                // Found an empty constructor
                return;
            }
        }

        this.structGenerator.getProcessingEnvironment()
                            .getMessager()
                            .printMessage(Diagnostic.Kind.ERROR,
                                          "@Struct annotated type must contain a public no-arg constructor.",
                                          typeElement);
    }

    private void isPublic(final TypeElement typeElement) {
        if (!typeElement.getModifiers()
                        .contains(Modifier.PUBLIC)) {
            this.structGenerator.getProcessingEnvironment()
                                .getMessager()
                                .printMessage(Diagnostic.Kind.ERROR,
                                              "@Struct annotation must be placed on a public type.",
                                              typeElement);
        }
    }

    private void isClass(final TypeElement typeElement) {
        if (!typeElement.getKind()
                        .isClass()) {
            this.structGenerator.getProcessingEnvironment()
                                .getMessager()
                                .printMessage(Diagnostic.Kind.ERROR,
                                              "@Struct annotation must be placed on a class type.",
                                              typeElement);
        }
    }
}
