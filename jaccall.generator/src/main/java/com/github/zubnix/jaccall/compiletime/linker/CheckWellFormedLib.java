package com.github.zubnix.jaccall.compiletime.linker;

import com.github.zubnix.jaccall.compiletime.MethodValidator;
import com.google.auto.common.SuperficialValidation;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.Set;

final class CheckWellFormedLib {

    private final LinkerGenerator linkerGenerator;

    private boolean inError;

    CheckWellFormedLib(final LinkerGenerator linkerGenerator) {this.linkerGenerator = linkerGenerator;}

    public boolean isInError() {
        return this.inError;
    }

    public void process(final Set<? extends TypeElement> typeElements) {

        for (final TypeElement typeElement : typeElements) {

            if (SuperficialValidation.validateElement(typeElement)) {

                isClass(typeElement);
                isNotNested(typeElement);

                final MethodValidator methodValidator = new MethodValidator(this.linkerGenerator.getProcessingEnvironment());

                for (final ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
                    methodValidator.validateIfNative(executableElement);
                    this.inError |= methodValidator.isInError();
                }
            }
            else {
                this.linkerGenerator.getProcessingEnvironment()
                                    .getMessager()
                                    .printMessage(Diagnostic.Kind.ERROR,
                                                  "Could not resolve all required compile time type information.",
                                                  typeElement);
                this.inError = true;
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
            this.inError = true;
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
            this.inError = true;
        }
    }
}