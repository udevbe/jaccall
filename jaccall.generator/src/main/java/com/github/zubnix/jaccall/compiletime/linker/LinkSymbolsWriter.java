package com.github.zubnix.jaccall.compiletime.linker;


import com.github.zubnix.jaccall.Lib;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.SetMultimap;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

public class LinkSymbolsWriter implements BasicAnnotationProcessor.ProcessingStep {
    private final LinkerGenerator linkerGenerator;

    public LinkSymbolsWriter(final LinkerGenerator linkerGenerator) {

        this.linkerGenerator = linkerGenerator;
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return Collections.singleton(Lib.class);
    }

    @Override
    public void process(final SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {

    }
}
