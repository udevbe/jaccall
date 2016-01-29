package com.github.zubnix.jaccall.compiletime;

import com.github.zubnix.jaccall.Functor;
import com.github.zubnix.jaccall.Lib;
import com.github.zubnix.jaccall.Struct;
import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.util.HashSet;
import java.util.Set;

@AutoService(Processor.class)
public class JaccallGenerator extends AbstractProcessor {

    private Messager messager;
    private Filer    filer;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>() {
            {
                add("com.github.zubnix.jaccall.Struct");
                add("com.github.zubnix.jaccall.Functor");
                add("com.github.zubnix.jaccall.Lib");
            }
        };
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations,
                           final RoundEnvironment roundEnv) {

        processStructs(roundEnv);
        processFunctors(roundEnv);
        processLib(roundEnv);

        return true;
    }

    private void processLib(final RoundEnvironment roundEnv) {
        final Set<TypeElement> typeElements = ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(Lib.class));

        new CheckWellFormedLib(this.messager).process(typeElements);
        new LinkSymbolsWriter(this.messager,
                              this.filer).process(typeElements);

    }

    private void processStructs(final RoundEnvironment roundEnv) {
        final Set<TypeElement> typeElements = ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(Struct.class));

        new CheckWellFormedStruct(this.messager).process(typeElements);
        new StructWriter(this.messager,
                         this.filer).process(typeElements);
    }

    private void processFunctors(final RoundEnvironment roundEnv) {
        final Set<TypeElement> typeElements = ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(Functor.class));

        new CheckWellFormedFunctor(this.messager).process(typeElements);
        new FunctorWriter(this.messager,
                          this.filer).process(typeElements);
    }
}
