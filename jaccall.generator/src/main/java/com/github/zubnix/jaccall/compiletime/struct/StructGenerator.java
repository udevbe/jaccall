package com.github.zubnix.jaccall.compiletime.struct;

import com.github.zubnix.jaccall.Struct;
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
@SupportedAnnotationTypes("com.github.zubnix.jaccall.Struct")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class StructGenerator extends AbstractProcessor {

    ProcessingEnvironment getProcessingEnvironment() {
        return this.processingEnv;
    }

    @Override
    public boolean process(final Set<? extends TypeElement> set,
                           final RoundEnvironment roundEnvironment) {
        final Set<TypeElement> typeElements = ElementFilter.typesIn(roundEnvironment.getElementsAnnotatedWith(Struct.class));

        final CheckWellFormedStruct checkWellFormedStruct = new CheckWellFormedStruct(this);
        checkWellFormedStruct.process(typeElements);
        if (!checkWellFormedStruct.isInError()) {
            new StructWriter(this).process(typeElements);
        }

        return true;
    }
}
