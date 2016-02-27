package com.github.zubnix.jaccall.compiletime;


import com.github.zubnix.jaccall.Symbol;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

final class CheckWellFormedFunctor {
    private final Messager messager;
    private       boolean  error;

    CheckWellFormedFunctor(final Messager messager) {
        this.messager = messager;
    }

    public boolean hasErrors(final TypeElement typeElement) {
        isTopLevel(typeElement);
        isInterface(typeElement);
        doesNotExtend(typeElement);
        hasSingleMethod(typeElement);
        isNotSymbol(typeElement);

        final MethodValidator methodValidator = new MethodValidator(this.messager);

        for (final ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
            hasDollarAsName(executableElement);
            methodValidator.validate(executableElement);
        }

        this.error |= methodValidator.errorRaised();

        return this.error;
    }

    private void raiseError() {
        this.error |= true;
    }

    private void isNotSymbol(final TypeElement typeElement) {
        for (final AnnotationMirror annotationMirror : typeElement.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType()
                                .asElement()
                                .getSimpleName()
                                .toString()
                                .equals(Symbol.class.getSimpleName())) {
                this.messager.printMessage(Diagnostic.Kind.ERROR,
                                           "@Symbol annotation can not be placed on functor.");
                raiseError();
                return;
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

    private void hasDollarAsName(final ExecutableElement executableElement) {
        if (!executableElement.getSimpleName()
                              .toString()
                              .equals("$")) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "Method name must be '$'.",
                                       executableElement);
            raiseError();
        }
    }

    private void doesNotExtend(final TypeElement typeElement) {
        if (typeElement.getInterfaces()
                       .size() > 0) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "Type may not extend other interfaces.",
                                       typeElement);
            raiseError();
        }
    }


    private void isInterface(final TypeElement typeElement) {
        if (!typeElement.getKind()
                        .isInterface()) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "Type must be an interface.",
                                       typeElement);
            raiseError();
        }
    }

    private void hasSingleMethod(final TypeElement typeElement) {
        if (ElementFilter.methodsIn(typeElement.getEnclosedElements())
                         .size() != 1) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "Type must have exactly one method.",
                                       typeElement);
            raiseError();
        }
    }
}