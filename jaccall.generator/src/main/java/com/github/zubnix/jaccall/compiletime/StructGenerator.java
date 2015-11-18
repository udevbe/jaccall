package com.github.zubnix.jaccall.compiletime;

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.service.AutoService;

import javax.annotation.processing.Processor;
import java.util.Collections;

@AutoService(Processor.class)
public class StructGenerator extends BasicAnnotationProcessor {

    @Override
    protected Iterable<? extends ProcessingStep> initSteps() {
        return Collections.emptyList();
    }
}
