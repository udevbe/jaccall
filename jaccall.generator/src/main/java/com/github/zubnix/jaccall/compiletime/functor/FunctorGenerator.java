package com.github.zubnix.jaccall.compiletime.functor;

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.service.AutoService;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import java.util.Arrays;

@AutoService(Processor.class)
public final class FunctorGenerator extends BasicAnnotationProcessor {

    public ProcessingEnvironment getProcessingEnvironment() {
        return this.processingEnv;
    }

    @Override
    protected Iterable<? extends ProcessingStep> initSteps() {
        return Arrays.asList(new CheckWellFormedFunctor(this),
                             new FunctorWriter(this));
    }
}
