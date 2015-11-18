package com.github.zubnix.jaccall.compiletime.linker;

import com.github.zubnix.jaccall.ByVal;
import com.github.zubnix.jaccall.Lib;
import com.github.zubnix.jaccall.Lng;
import com.github.zubnix.jaccall.Ptr;
import com.github.zubnix.jaccall.Unsigned;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.SetMultimap;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
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
            TypeElement typeElement = (TypeElement) element;

            isClass(typeElement);

            for (Element enclosedElement : typeElement.getEnclosedElements()) {
                isWellFormedMethod(enclosedElement);
            }
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

            if (enclosedElement.getModifiers()
                               .contains(Modifier.NATIVE)) {
                //we're only interested if native methods are properly formed
                ExecutableType executableType = (ExecutableType) enclosedElement;
                for (TypeMirror typeMirror : executableType.getParameterTypes()) {
                    isAllowedType(enclosedElement,
                                  typeMirror);
                    hasAllowedAnnotations(enclosedElement,
                                          typeMirror);
                }

                final TypeMirror returnType = executableType.getReturnType();
                isAllowedType(enclosedElement,
                              returnType);
                hasAllowedAnnotations(enclosedElement,
                                      returnType);
            }
        }
    }

    private void hasAllowedAnnotations(final Element enclosedElement,
                                       final TypeMirror typeMirror) {
        //only type 'long' can have @Ptr annotation
        hasWellPlacedPtr(enclosedElement,
                         typeMirror);
        //only type 'long' can have ByVal annotation, and can not be used in conjunction with @Ptr or @Unsigned
        hasWellPlacedByVal(enclosedElement,
                           typeMirror);
        //only type 'long' can have @Lng annotation
        hasWellPlacedLng(enclosedElement,
                         typeMirror);
        //float, double and 'long' annotated with @ByVal or @Ptr can not have @Unsigned annotation
        hasWellPlacedUnsigned(enclosedElement,
                              typeMirror);
    }

    private void hasWellPlacedUnsigned(final Element element,
                                       final TypeMirror typeMirror) {
        isNotTypeKind(element,
                      typeMirror,
                      TypeKind.FLOAT,
                      "@Unsigned annotation can not be placed on primitive type 'float'.");

        isNotTypeKind(element,
                      typeMirror,
                      TypeKind.DOUBLE,
                      "@Unsigned annotation can not be placed on primitive type 'double'.");

        isNotPtr(element,
                 typeMirror,
                 "@Unsigned annotation can not be placed in conjunction with @Ptr annotation.");

        isNotByVal(element,
                   typeMirror,
                   "@Unsigned annotation can not be placed in conjunction with @ByVal annotation.");
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

    private void hasWellPlacedLng(final Element element,
                                  final TypeMirror typeMirror) {
        if (typeMirror.getAnnotation(Lng.class) != null) {
            isLong(element,
                   typeMirror,
                   "@Lng annotation can only be placed on primitive type 'long'.");
            isNotPtr(element,
                     typeMirror,
                     "@Lng annotation can not be placed in conjunction with @Ptr annotation.");

            isNotByVal(element,
                       typeMirror,
                       "@Lng annotation can not be placed in conjunction with @ByVal annotation.");
        }
    }

    private void isNotByVal(final Element element,
                            final TypeMirror typeMirror,
                            final String charSequence) {
        if (typeMirror.getAnnotation(ByVal.class) != null) {
            linkerGenerator.getProcessingEnvironment()
                           .getMessager()
                           .printMessage(Diagnostic.Kind.ERROR,
                                         charSequence,
                                         element);
        }
    }

    private void hasWellPlacedByVal(final Element element,
                                    final TypeMirror typeMirror) {
        if (typeMirror.getAnnotation(ByVal.class) != null) {

            isLong(element,
                   typeMirror,
                   "@ByVal annotation can only be placed on primitive type 'long'.");
            isNotPtr(element,
                     typeMirror,
                     "@ByVal annotation can not be placed in conjunction with @Ptr annotation.");
            isNotUnsigned(element,
                          typeMirror);
        }
    }

    private void isNotUnsigned(final Element element,
                               final TypeMirror typeMirror) {
        if (typeMirror.getAnnotation(Unsigned.class) != null) {
            linkerGenerator.getProcessingEnvironment()
                           .getMessager()
                           .printMessage(Diagnostic.Kind.ERROR,
                                         "@ByVal annotation can not be placed in conjunction with @Unsigned annotation.",
                                         element);
        }
    }

    private void isNotPtr(final Element element,
                          final TypeMirror typeMirror,
                          final String charSequence) {
        if (typeMirror.getAnnotation(Ptr.class) != null) {
            linkerGenerator.getProcessingEnvironment()
                           .getMessager()
                           .printMessage(Diagnostic.Kind.ERROR,
                                         charSequence,
                                         element);
        }
    }

    private void hasWellPlacedPtr(final Element element,
                                  final TypeMirror typeMirror) {
        if (typeMirror.getAnnotation(Ptr.class) != null) {
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

    private void isAllowedType(final Element element,
                               TypeMirror typeMirror) {
        final TypeKind kind = typeMirror.getKind();
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
