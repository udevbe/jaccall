package org.freedesktop.jaccall.compiletime;

import com.google.auto.service.AutoService;
import org.freedesktop.jaccall.Functor;
import org.freedesktop.jaccall.Lib;
import org.freedesktop.jaccall.Struct;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"org.freedesktop.jaccall.Struct",
                           "org.freedesktop.jaccall.Functor",
                           "org.freedesktop.jaccall.Lib"})
public class JaccallGenerator extends AbstractProcessor {

    private Messager messager;
    private Filer    filer;
    private Elements elementUtils;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elementUtils = processingEnv.getElementUtils();
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

        for (final TypeElement typeElement : typeElements) {
            if (!new CheckWellFormedLib(this.messager).hasErrors(typeElement)) {
                new SymbolsWriter(this.messager,
                                  this.filer).process(typeElement);
            }
        }
    }

    private void processStructs(final RoundEnvironment roundEnv) {
        final Set<TypeElement> typeElements = ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(Struct.class));

        for (final TypeElement typeElement : typeElements) {
            if (!new CheckWellFormedStruct(this.messager).hasErrors(typeElement)) {
                new StructWriter(this.messager,
                                 this.filer).process(typeElement);
            }
        }
    }

    private void processFunctors(final RoundEnvironment roundEnv) {
        final Set<TypeElement> typeElements = ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(Functor.class));

        for (final TypeElement typeElement : typeElements) {
            if (!new CheckWellFormedFunctor(this.messager).hasErrors(typeElement)) {
                new FunctorWriter(this.messager,
                                  this.filer,
                                  this.elementUtils).process(typeElement);
            }
        }
    }
}
