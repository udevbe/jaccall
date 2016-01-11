package com.github.zubnix.jaccall.compiletime.funcptr;

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.service.AutoService;

import javax.annotation.processing.Processor;

@AutoService(Processor.class)
public class FunctionPointerGenerator extends BasicAnnotationProcessor {
    @Override
    protected Iterable<? extends ProcessingStep> initSteps() {
        return null;
    }
}
