package com.github.zubnix.jaccall.compiletime;

import com.github.zubnix.jaccall.Symbol;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

final class CheckWellFormedLib {

    private final Messager messager;
    private       boolean  error;

    CheckWellFormedLib(final Messager messager) {
        this.messager = messager;
    }

    public boolean hasErrors(final TypeElement typeElement) {

        isClass(typeElement);
        isNotNested(typeElement);

        final MethodValidator methodValidator = new MethodValidator(this.messager);

        for (final ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {

            boolean symbol = false;
            for (final AnnotationMirror annotationMirror : executableElement.getAnnotationMirrors()) {
                if (annotationMirror.getAnnotationType()
                                    .asElement()
                                    .getSimpleName()
                                    .toString()
                                    .equals(Symbol.class.getSimpleName())) {
                    symbol = true;
                    break;
                }
            }

            //validate as C symbol
            if (symbol) {
                methodValidator.validateSymbol(executableElement);
            }
            else {
                //validate as C method
                methodValidator.validateIfNative(executableElement);
            }
        }

        this.error |= methodValidator.errorRaised();

        return this.error;
    }

    private void raiseError() {
        this.error |= true;
    }

    private void isNotNested(final TypeElement typeElement) {
        if (!typeElement.getEnclosingElement()
                        .getKind()
                        .equals(ElementKind.PACKAGE) || typeElement.getNestingKind()
                                                                   .isNested()) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "@Lib annotation should be placed on top level class types only.",
                                       typeElement);
            raiseError();
        }
    }

    private void isClass(final TypeElement typeElement) {
        if (!typeElement.getKind()
                        .equals(ElementKind.CLASS)) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "@Lib annotation should be placed on class type only.",
                                       typeElement);
            raiseError();
        }
    }
}