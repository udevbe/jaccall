package com.github.zubnix.jaccall.compiletime.linker;


import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.service.AutoService;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import java.util.Arrays;

@AutoService(Processor.class)
public class LinkerGenerator extends BasicAnnotationProcessor {

    public ProcessingEnvironment getProcessingEnvironment() {
        return this.processingEnv;
    }

    @Override
    protected Iterable<? extends ProcessingStep> initSteps() {
        return Arrays.asList(new CheckWellFormedLib(this),
                             new LinkSymbolsWriter(this));
    }

}
