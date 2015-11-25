package com.github.zubnix.jaccall.compiletime.functor;

import com.github.zubnix.jaccall.Functor;
import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.util.Set;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("com.github.zubnix.jaccall.Functor")
public class FunctorGenerator extends AbstractProcessor {

    public ProcessingEnvironment getProcessingEnvironment() {
        return this.processingEnv;
    }

    @Override
    public boolean process(final Set<? extends TypeElement> set,
                           final RoundEnvironment roundEnvironment) {

        final Set<TypeElement> typeElements = ElementFilter.typesIn(roundEnvironment.getElementsAnnotatedWith(Functor.class));

        final CheckWellFormedFunctor checkWellFormedFunctor = new CheckWellFormedFunctor(this);
        checkWellFormedFunctor.process(typeElements);

        if (!checkWellFormedFunctor.isInError()) {
            new FunctorWriter(this).process(typeElements);
        }

        return true;
    }
}
