package com.github.zubnix.jaccall.compiletime.linker;


import com.github.zubnix.jaccall.Functor;
import com.github.zubnix.jaccall.Lib;
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
@SupportedAnnotationTypes("com.github.zubnix.jaccall.Lib")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class LinkerGenerator extends AbstractProcessor {

    public ProcessingEnvironment getProcessingEnvironment() {
        return this.processingEnv;
    }

    @Override
    public boolean process(final Set<? extends TypeElement> set,
                           final RoundEnvironment roundEnvironment) {
        final Set<TypeElement> typeElements = ElementFilter.typesIn(roundEnvironment.getElementsAnnotatedWith(Lib.class));


        final CheckWellFormedLib checkWellFormedLib = new CheckWellFormedLib(this);
        checkWellFormedLib.process(typeElements);

        if (!checkWellFormedLib.isInError()) {
            new LinkSymbolsWriter(this).process(typeElements);
        }

        return true;
    }
}
