package com.github.zubnix.jaccall.compiletime.functor;


import com.github.zubnix.jaccall.compiletime.MethodValidator;
import com.google.auto.common.SuperficialValidation;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.Collections;
import java.util.Set;

final class CheckWellFormedFunctor {
    private       boolean          inError;
    private final FunctorGenerator functorGenerator;

    CheckWellFormedFunctor(final FunctorGenerator functorGenerator) {

        this.functorGenerator = functorGenerator;
    }

    public boolean isInError() {
        return this.inError;
    }

    public void process(final Set<? extends TypeElement> typeElements) {
        for (final TypeElement typeElement : typeElements) {
            if (SuperficialValidation.validateElement(typeElement)) {
                isInterface(typeElement);
                doesNotExtend(typeElement);
                hasSingleMethod(typeElement);

                final MethodValidator methodValidator = new MethodValidator(this.functorGenerator.getProcessingEnvironment());

                for (final ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
                    hasDollarAsName(executableElement);
                    methodValidator.validate(executableElement);
                    this.inError |= methodValidator.isInError();
                }
            }
            else {
                this.inError = true;
                this.functorGenerator.getProcessingEnvironment()
                                     .getMessager()
                                     .printMessage(Diagnostic.Kind.ERROR,
                                                   "Could not resolve all required compile time type information.",
                                                   typeElement);
            }
        }
    }

    private void hasDollarAsName(final ExecutableElement executableElement) {
        if (!executableElement.getSimpleName()
                              .toString()
                              .equals("$")) {
            this.inError = true;
            this.functorGenerator.getProcessingEnvironment()
                                 .getMessager()
                                 .printMessage(Diagnostic.Kind.ERROR,
                                               "Method name must be '$'.",
                                               executableElement);
        }
    }

    private void doesNotExtend(final TypeElement typeElement) {
        if (typeElement.getInterfaces()
                       .size() > 0) {
            this.inError = true;
            this.functorGenerator.getProcessingEnvironment()
                                 .getMessager()
                                 .printMessage(Diagnostic.Kind.ERROR,
                                               "Type may not extend other interfaces.",
                                               typeElement);
        }
    }


    private void isInterface(final TypeElement typeElement) {
        if (!typeElement.getKind()
                        .isInterface()) {
            this.inError = true;
            this.functorGenerator.getProcessingEnvironment()
                                 .getMessager()
                                 .printMessage(Diagnostic.Kind.ERROR,
                                               "Type must be an interface.",
                                               typeElement);
        }
    }

    private void hasSingleMethod(final TypeElement typeElement) {
        if (ElementFilter.methodsIn(Collections.singleton(typeElement))
                         .size() != 1) {
            this.inError = true;
            this.functorGenerator.getProcessingEnvironment()
                                 .getMessager()
                                 .printMessage(Diagnostic.Kind.ERROR,
                                               "Type must have exactly one method.",
                                               typeElement);
        }
    }
}