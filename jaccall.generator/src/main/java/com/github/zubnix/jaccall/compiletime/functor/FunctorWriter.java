package com.github.zubnix.jaccall.compiletime.functor;


import com.github.zubnix.jaccall.Functor;
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

final class FunctorWriter implements BasicAnnotationProcessor.ProcessingStep {

    private final FunctorGenerator functorGenerator;

    FunctorWriter(final FunctorGenerator functorGenerator) {

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
                for (final ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
                    writeFunctorImplementation(executableElement);
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

    private void writeFunctorImplementation(final ExecutableElement executableElement) {
        writeFactory(executableElement);
        writeCFunctor(executableElement);
        writeJavaFunctor(executableElement);
    }

    private void writeJavaFunctor(final ExecutableElement executableElement) {

    }

    private void writeCFunctor(final ExecutableElement executableElement) {

    }

    private void writeFactory(final ExecutableElement executableElement) {

    }
}
