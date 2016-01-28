package com.github.zubnix.jaccall.compiletime.functor;


import com.github.zubnix.jaccall.Functor;
import com.github.zubnix.jaccall.compiletime.MethodValidator;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.common.SuperficialValidation;
import com.google.common.collect.SetMultimap;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

final class CheckWellFormedFunctor implements BasicAnnotationProcessor.ProcessingStep {
    private final FunctorGenerator functorGenerator;

    CheckWellFormedFunctor(final FunctorGenerator functorGenerator) {

        this.functorGenerator = functorGenerator;
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return Collections.singleton(Functor.class);
    }

    @Override
    public void process(final SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        for (final TypeElement typeElement : ElementFilter.typesIn(elementsByAnnotation.values())) {
            if (SuperficialValidation.validateElement(typeElement)) {
                isInterface(typeElement);
                doesNotExtend(typeElement);
                hasSingleMethod(typeElement);

                final MethodValidator methodValidator = new MethodValidator(this.functorGenerator.getProcessingEnvironment());

                for (final ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
                    methodValidator.validate(executableElement);
                }
            }
            else {
                this.functorGenerator.getProcessingEnvironment()
                                     .getMessager()
                                     .printMessage(Diagnostic.Kind.ERROR,
                                                   "Could not resolve all required compile time type information.",
                                                   typeElement);
            }
        }
    }

    private void doesNotExtend(final TypeElement typeElement) {
        if (typeElement.getInterfaces()
                       .size() > 0) {
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
            this.functorGenerator.getProcessingEnvironment()
                                 .getMessager()
                                 .printMessage(Diagnostic.Kind.ERROR,
                                               "Type must have exactly one method.",
                                               typeElement);
        }
    }
}