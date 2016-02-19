package com.github.zubnix.jaccall.compiletime;


import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.Set;

final class CheckWellFormedFunctor {
    private final Messager messager;

    CheckWellFormedFunctor(final Messager messager) {
        this.messager = messager;
    }

    public void process(final Set<? extends TypeElement> typeElements) {
        for (final TypeElement typeElement : typeElements) {
            isTopLevel(typeElement);
            isInterface(typeElement);
            doesNotExtend(typeElement);
            hasSingleMethod(typeElement);

            final MethodValidator methodValidator = new MethodValidator(this.messager);

            for (final ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
                hasDollarAsName(executableElement);
                methodValidator.validate(executableElement);
            }
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

    private void hasDollarAsName(final ExecutableElement executableElement) {
        if (!executableElement.getSimpleName()
                              .toString()
                              .equals("$")) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "Method name must be '$'.",
                                       executableElement);
        }
    }

    private void doesNotExtend(final TypeElement typeElement) {
        if (typeElement.getInterfaces()
                       .size() > 0) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "Type may not extend other interfaces.",
                                       typeElement);
        }
    }


    private void isInterface(final TypeElement typeElement) {
        if (!typeElement.getKind()
                        .isInterface()) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "Type must be an interface.",
                                       typeElement);
        }
    }

    private void hasSingleMethod(final TypeElement typeElement) {
        if (ElementFilter.methodsIn(typeElement.getEnclosedElements())
                         .size() != 1) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "Type must have exactly one method.",
                                       typeElement);
        }
    }
}