package com.github.zubnix.jaccall.compiletime;

import com.github.zubnix.jaccall.GlobalVar;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.Set;

final class CheckWellFormedLib {

    private final Messager messager;

    CheckWellFormedLib(final Messager messager) {
        this.messager = messager;
    }

    public void process(final Set<? extends TypeElement> typeElements) {

        for (final TypeElement typeElement : typeElements) {

            isClass(typeElement);
            isNotNested(typeElement);

            final MethodValidator methodValidator = new MethodValidator(this.messager);

            for (final ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {

                boolean globalVar = false;
                for (final AnnotationMirror annotationMirror : executableElement.getAnnotationMirrors()) {
                    if (annotationMirror.getAnnotationType()
                                        .asElement()
                                        .getSimpleName()
                                        .toString()
                                        .equals(GlobalVar.class.getSimpleName())) {
                        globalVar = true;
                        break;
                    }
                }

                //validate as C global variable
                if (globalVar) {
                    methodValidator.validateGlobalVar(executableElement);
                }
                else {
                    //validate as C method
                    methodValidator.validateIfNative(executableElement);
                }
            }
        }
    }

    private void isNotNested(final TypeElement typeElement) {
        if (!typeElement.getEnclosingElement()
                        .getKind()
                        .equals(ElementKind.PACKAGE) || typeElement.getNestingKind()
                                                                   .isNested()) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "@Lib annotation should be placed on top level class types only.",
                                       typeElement);
        }
    }

    private void isClass(final TypeElement typeElement) {
        if (!typeElement.getKind()
                        .equals(ElementKind.CLASS)) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "@Lib annotation should be placed on class type only.",
                                       typeElement);
        }
    }
}