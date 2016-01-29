package com.github.zubnix.jaccall.compiletime;

import com.github.zubnix.jaccall.Struct;

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

    CheckWellFormedStruct(final Messager messager) {

        this.messager = messager;
    }

    public void process(final Set<? extends TypeElement> typeElements) {
        for (final TypeElement typeElement : typeElements) {
            //TODO we should allow non-top level structs and simply fail on name clash
            isTopLevel(typeElement);

            isClass(typeElement);
            isPublic(typeElement);
            isFinal(typeElement);
            isNotAbstract(typeElement);
            hasDefaultConstructor(typeElement);
            hasNonEmptyStructAnnotation(typeElement);
            doesNotHaveStaticSIZEField(typeElement);
            extendsGeneratedStructType(typeElement);
            doesNotHaveSameNameFields(typeElement);
        }
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

    private void extendsGeneratedStructType(final TypeElement typeElement) {

        final String typeName = typeElement.getSimpleName()
                                           .toString();
        final String expectedSuperTypeName = typeName + "_Jaccall_StructType";

        final Element enclosingElement = typeElement.getEnclosingElement();
        if (!enclosingElement.getKind()
                             .equals(ElementKind.PACKAGE)) {
            return;
        }

        final PackageElement packageElement = (PackageElement) enclosingElement;
        final String expectedPackage = packageElement.getQualifiedName()
                                                     .toString();

        final TypeMirror superclass = typeElement.getSuperclass();
        if (superclass.getKind()
                      .equals(TypeKind.NONE)) {
            return;
        }

        final DeclaredType declaredType     = (DeclaredType) superclass;
        final Element      superTypeElement = declaredType.asElement();
        final String superTypeName = superTypeElement.getSimpleName()
                                                     .toString();

        if (!superTypeName.equals(expectedSuperTypeName)) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "@Struct annotation should be placed on type that extends '" + expectedSuperTypeName + "' from the same package'",
                                       typeElement);
        }
    }

    private void isTopLevel(final TypeElement typeElement) {
        if (typeElement.getNestingKind()
                       .isNested()) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
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
                this.messager.printMessage(Diagnostic.Kind.ERROR,
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
                             .toString()
                             .equals("value")) {
                        final List<? extends AnnotationValue> values = (List<? extends AnnotationValue>) entry.getValue()
                                                                                                              .getValue();
                        if (values.isEmpty()) {
                            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                                       "@Struct annotation must have at least one field.",
                                                       typeElement);
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
    }

    private void isFinal(final TypeElement typeElement) {
        if (!typeElement.getModifiers()
                        .contains(Modifier.FINAL)) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "@Struct annotation must be placed on a final type.",
                                       typeElement);
        }
    }

    private void isPublic(final TypeElement typeElement) {
        if (!typeElement.getModifiers()
                        .contains(Modifier.PUBLIC)) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "@Struct annotation must be placed on a public type.",
                                       typeElement);
        }
    }

    private void isClass(final TypeElement typeElement) {
        if (!typeElement.getKind()
                        .isClass()) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "@Struct annotation must be placed on a class type.",
                                       typeElement);
        }
    }
}
