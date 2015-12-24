package com.github.zubnix.jaccall.compiletime.struct;

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.service.AutoService;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import java.util.Arrays;

@AutoService(Processor.class)
public final class StructGenerator extends BasicAnnotationProcessor {

    @Override
    protected Iterable<? extends ProcessingStep> initSteps() {
        return Arrays.asList(new CheckWellFormedStruct(this));
    }

    ProcessingEnvironment getProcessingEnvironment(){
        return this.processingEnv;
    }
}
