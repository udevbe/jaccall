package org.freedesktop.jaccall.compiletime;

import org.freedesktop.jaccall.Struct;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class CheckWellFormedStruct {

    private final Messager messager;
    private       boolean  error;

    CheckWellFormedStruct(final Messager messager) {

        this.messager = messager;
    }

    public boolean hasErrors(final TypeElement typeElement) {
        isTopLevel(typeElement);
        isClass(typeElement);
        isPublic(typeElement);
        isFinal(typeElement);
        isNotAbstract(typeElement);
        hasDefaultConstructor(typeElement);
        hasNonEmptyStructAnnotation(typeElement);
        doesNotHaveStaticSIZEField(typeElement);
        doesNotHaveSameNameFields(typeElement);

        return this.error;
    }

    private void raiseError() {
        this.error |= true;
    }

    private void doesNotHaveSameNameFields(final TypeElement typeElement) {
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
                             .toString()
                             .equals("value")) {
                        final List<? extends AnnotationValue> values = (List<? extends AnnotationValue>) entry.getValue()
                                                                                                              .getValue();
                        final Set<String> fieldNames = new HashSet<>();
                        for (final AnnotationValue annotationValue : values) {
                            final AnnotationMirror fieldMirror = (AnnotationMirror) annotationValue;
                            for (final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> fieldValue : fieldMirror.getElementValues()
                                                                                                                                 .entrySet()) {
                                if (fieldValue.getKey()
                                              .getSimpleName()
                                              .toString()
                                              .equals("name")) {
                                    final String fieldName = (String) fieldValue.getValue()
                                                                                .getValue();
                                    if (fieldNames.contains(fieldName)) {
                                        this.messager.printMessage(Diagnostic.Kind.ERROR,
                                                                   "@Struct annotation has duplicated field name '" + fieldName + "'.",
                                                                   typeElement);
                                        raiseError();
                                    }
                                    else {
                                        fieldNames.add(fieldName);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void isTopLevel(final TypeElement typeElement) {
        if (typeElement.getNestingKind()
                       .isNested()) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "@Struct annotation should be placed on top level class types only.",
                                       typeElement);
            raiseError();
        }
    }

    private void doesNotHaveStaticSIZEField(final TypeElement typeElement) {
        for (final VariableElement variableElement : ElementFilter.fieldsIn(typeElement.getEnclosedElements())) {
            if (variableElement.getModifiers()
                               .contains(Modifier.STATIC) && variableElement.getSimpleName()
                                                                            .toString()
                                                                            .equals("SIZE")) {
                this.messager.printMessage(Diagnostic.Kind.ERROR,
                                           "@Struct type may not contain a static field with name SIZE.",
                                           typeElement);
                raiseError();
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
                             .toString()
                             .equals("value")) {
                        final List<? extends AnnotationValue> values = (List<? extends AnnotationValue>) entry.getValue()
                                                                                                              .getValue();
                        if (values.isEmpty()) {
                            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                                       "@Struct annotation must have at least one field.",
                                                       typeElement);
                            raiseError();
                        }
                    }
                }
            }
        }
    }

    private void isNotAbstract(final TypeElement typeElement) {
        if (typeElement.getModifiers()
                       .contains(Modifier.ABSTRACT)) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "@Struct annotation can not be placed on an abstract type.",
                                       typeElement);
            raiseError();
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

        this.messager.printMessage(Diagnostic.Kind.ERROR,
                                   "@Struct annotated type must contain a public no-arg constructor.",
                                   typeElement);
        raiseError();
    }

    private void isFinal(final TypeElement typeElement) {
        if (!typeElement.getModifiers()
                        .contains(Modifier.FINAL)) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "@Struct annotation must be placed on a final type.",
                                       typeElement);
            raiseError();
        }
    }

    private void isPublic(final TypeElement typeElement) {
        if (!typeElement.getModifiers()
                        .contains(Modifier.PUBLIC)) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "@Struct annotation must be placed on a public type.",
                                       typeElement);
            raiseError();
        }
    }

    private void isClass(final TypeElement typeElement) {
        if (!typeElement.getKind()
                        .isClass()) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "@Struct annotation must be placed on a class type.",
                                       typeElement);
            raiseError();
        }
    }
}
