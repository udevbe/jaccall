package com.github.zubnix.jaccall.compiletime.linker;

import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.Lib;
import com.github.zubnix.jaccall.Lng;
import com.github.zubnix.jaccall.Ptr;
import com.github.zubnix.jaccall.Unsigned;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.common.SuperficialValidation;
import com.google.common.collect.SetMultimap;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

public class CheckWellFormedLib implements BasicAnnotationProcessor.ProcessingStep {
    private LinkerGenerator linkerGenerator;

    public CheckWellFormedLib(final LinkerGenerator linkerGenerator) {this.linkerGenerator = linkerGenerator;}

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return Collections.singleton(Lib.class);
    }

    @Override
    public void process(final SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        for (Element element : elementsByAnnotation.values()) {
            if (SuperficialValidation.validateElement(element)) {

                TypeElement typeElement = (TypeElement) element;

                isClass(typeElement);
                isNotNested(typeElement);

                for (Element enclosedElement : typeElement.getEnclosedElements()) {
                    isWellFormedMethod(enclosedElement);
                }
            }
            else {
                linkerGenerator.getProcessingEnvironment()
                               .getMessager()
                               .printMessage(Diagnostic.Kind.ERROR,
                                             "Could not resolve all required compile time type information.",
                                             element);
            }
        }
    }

    private void isNotNested(final TypeElement typeElement) {
        if (!typeElement.getEnclosingElement()
                        .getKind()
                        .equals(ElementKind.PACKAGE) || typeElement.getNestingKind()
                                                                   .isNested()) {
            linkerGenerator.getProcessingEnvironment()
                           .getMessager()
                           .printMessage(Diagnostic.Kind.ERROR,
                                         "@Lib annotation should be placed on top level class types only.",
                                         typeElement);
        }
    }

    private void isClass(TypeElement typeElement) {
        if (!typeElement.getKind()
                        .equals(ElementKind.CLASS)) {
            linkerGenerator.getProcessingEnvironment()
                           .getMessager()
                           .printMessage(Diagnostic.Kind.ERROR,
                                         "@Lib annotation should be placed on class type only.",
                                         typeElement);
        }
    }

    private void isWellFormedMethod(final Element enclosedElement) {
        if (enclosedElement.getKind()
                           .equals(ElementKind.METHOD)) {
            ExecutableElement executableElement = (ExecutableElement) enclosedElement;

            if (enclosedElement.getModifiers()
                               .contains(Modifier.NATIVE)) {
                //we're only interested if native methods are properly formed

                for (VariableElement variableElement : executableElement.getParameters()) {
                    isAllowedElement(variableElement,
                                     variableElement.asType()
                                                    .getKind());
                    hasAllowedAnnotations(variableElement.asType(),
                                          variableElement);
                }

                isAllowedElement(executableElement,
                                 executableElement.getReturnType()
                                                  .getKind());
                hasAllowedAnnotations(executableElement.getReturnType(),
                                      executableElement);
            }
        }
    }

    private void hasAllowedAnnotations(final TypeMirror type,
                                       final Element element) {
        //only type 'long' can have @Ptr annotation
        hasWellPlacedPtr(type,
                         element);
        //only type 'long' can have ByVal annotation, and can not be used in conjunction with @Ptr or @Unsigned
        hasWellPlacedByVal(type,
                           element);
        //only type 'long' can have @Lng annotation
        hasWellPlacedLng(type,
                         element);
        //float, double and 'long' annotated with @ByVal or @Ptr can not have @Unsigned annotation
        hasWellPlacedUnsigned(type,
                              element);
    }

    private void hasWellPlacedUnsigned(final TypeMirror typeMirror,
                                       final Element element) {
        if (element.getAnnotation(Unsigned.class) != null) {
            isNotTypeKind(element,
                          typeMirror,
                          TypeKind.FLOAT,
                          "@Unsigned annotation can not be placed on primitive type 'float'.");

            isNotTypeKind(element,
                          typeMirror,
                          TypeKind.DOUBLE,
                          "@Unsigned annotation can not be placed on primitive type 'double'.");

            isNotPtr(element,
                     "@Unsigned annotation can not be placed in conjunction with @Ptr annotation.");
        }
    }

    private void isNotTypeKind(final Element element,
                               final TypeMirror typeMirror,
                               final TypeKind aDouble,
                               final String charSequence) {
        if (typeMirror.getKind()
                      .equals(aDouble)) {
            linkerGenerator.getProcessingEnvironment()
                           .getMessager()
                           .printMessage(Diagnostic.Kind.ERROR,
                                         charSequence,
                                         element);
        }
    }

    private void hasWellPlacedLng(final TypeMirror typeMirror,
                                  final Element element) {
        if (element.getAnnotation(Lng.class) != null) {
            isLong(element,
                   typeMirror,
                   "@Lng annotation can only be placed on primitive type 'long'.");
            isNotPtr(element,
                     "@Lng annotation can not be placed in conjunction with @Ptr annotation.");

            isNotByVal(element,
                       "@Lng annotation can not be placed in conjunction with @ByVal annotation.");
        }
    }

    private void isNotByVal(final Element element,
                            final String charSequence) {
        if (element.getAnnotation(ByVal.class) != null) {
            linkerGenerator.getProcessingEnvironment()
                           .getMessager()
                           .printMessage(Diagnostic.Kind.ERROR,
                                         charSequence,
                                         element);
        }
    }

    private void hasWellPlacedByVal(final TypeMirror typeMirror,
                                    final Element element) {
        if (element.getAnnotation(ByVal.class) != null) {
            isLong(element,
                   typeMirror,
                   "@ByVal annotation can only be placed on primitive type 'long'.");
            isNotPtr(element,
                     "@ByVal annotation can not be placed in conjunction with @Ptr annotation.");
            isNotUnsigned(element);
        }
    }

    private void isNotUnsigned(final Element element) {
        if (element.getAnnotation(Unsigned.class) != null) {
            linkerGenerator.getProcessingEnvironment()
                           .getMessager()
                           .printMessage(Diagnostic.Kind.ERROR,
                                         "@ByVal annotation can not be placed in conjunction with @Unsigned annotation.",
                                         element);
        }
    }

    private void isNotPtr(final Element element,
                          final String charSequence) {
        if (element.getAnnotation(Ptr.class) != null) {
            linkerGenerator.getProcessingEnvironment()
                           .getMessager()
                           .printMessage(Diagnostic.Kind.ERROR,
                                         charSequence,
                                         element);
        }
    }

    private void hasWellPlacedPtr(final TypeMirror typeMirror,
                                  final Element element) {
        if (element.getAnnotation(Ptr.class) != null) {
            isLong(element,
                   typeMirror,
                   "@Ptr annotation can only be placed on primitive type 'long'.");
        }
    }

    private void isLong(final Element element,
                        final TypeMirror typeMirror,
                        final String charSequence) {
        if (!typeMirror.getKind()
                       .equals(TypeKind.LONG)) {
            linkerGenerator.getProcessingEnvironment()
                           .getMessager()
                           .printMessage(Diagnostic.Kind.ERROR,
                                         charSequence,
                                         element);

        }
    }

    private void isAllowedElement(Element element,
                                  final TypeKind kind) {
        //only primitive parameter and return types are allowed
        isPrimitive(element,
                    kind);
        //'boolean' and 'char' primitives are not allowed
        isAllowedPrimitive(element,
                           kind);
    }

    private void isPrimitive(final Element element,
                             final TypeKind kind) {
        if (!kind.isPrimitive()) {
            linkerGenerator.getProcessingEnvironment()
                           .getMessager()
                           .printMessage(Diagnostic.Kind.ERROR,
                                         "Native method should have supported primitive types only.",
                                         element);
        }
    }

    private void isAllowedPrimitive(final Element element,
                                    final TypeKind kind) {
        if (kind.equals(TypeKind.BOOLEAN)) {
            linkerGenerator.getProcessingEnvironment()
                           .getMessager()
                           .printMessage(Diagnostic.Kind.ERROR,
                                         "Native method should not have a primitive type 'boolean'.",
                                         element);
        }

        if (kind.equals(TypeKind.CHAR)) {
            linkerGenerator.getProcessingEnvironment()
                           .getMessager()
                           .printMessage(Diagnostic.Kind.ERROR,
                                         "Native method should not have a primitive type 'char'.",
                                         element);
        }
    }
}
