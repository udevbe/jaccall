package com.github.zubnix.jaccall.compiletime.linker;

import com.github.zubnix.jaccall.Lib;
import com.github.zubnix.jaccall.compiletime.MethodValidator;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.common.SuperficialValidation;
import com.google.common.collect.SetMultimap;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

final class CheckWellFormedLib implements BasicAnnotationProcessor.ProcessingStep {

    private final LinkerGenerator linkerGenerator;

    CheckWellFormedLib(final LinkerGenerator linkerGenerator) {this.linkerGenerator = linkerGenerator;}

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return Collections.singleton(Lib.class);
    }

    @Override
    public void process(final SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {

        for (final TypeElement typeElement : ElementFilter.typesIn(elementsByAnnotation.values())) {

            if (SuperficialValidation.validateElement(typeElement)) {

                isClass(typeElement);
                isNotNested(typeElement);

                final MethodValidator methodValidator = new MethodValidator(this.linkerGenerator.getProcessingEnvironment());

                for (final ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
                    methodValidator.validateIfNative(executableElement);
                }

            }
            else {
                this.linkerGenerator.getProcessingEnvironment()
                                    .getMessager()
                                    .printMessage(Diagnostic.Kind.ERROR,
                                                  "Could not resolve all required compile time type information.",
                                                  typeElement);
            }
        }
    }

    private void isNotNested(final TypeElement typeElement) {
        if (!typeElement.getEnclosingElement()
                        .getKind()
                        .equals(ElementKind.PACKAGE) || typeElement.getNestingKind()
                                                                   .isNested()) {
            this.linkerGenerator.getProcessingEnvironment()
                                .getMessager()
                                .printMessage(Diagnostic.Kind.ERROR,
                                              "@Lib annotation should be placed on top level class types only.",
                                              typeElement);
        }
    }

    private void isClass(final TypeElement typeElement) {
        if (!typeElement.getKind()
                        .equals(ElementKind.CLASS)) {
            this.linkerGenerator.getProcessingEnvironment()
                                .getMessager()
                                .printMessage(Diagnostic.Kind.ERROR,
                                              "@Lib annotation should be placed on class type only.",
                                              typeElement);
        }
    }
}