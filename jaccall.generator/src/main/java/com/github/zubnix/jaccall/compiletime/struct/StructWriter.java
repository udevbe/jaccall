package com.github.zubnix.jaccall.compiletime.struct;

import com.github.zubnix.jaccall.Struct;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.SetMultimap;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StructWriter implements BasicAnnotationProcessor.ProcessingStep {
    private final StructGenerator structGenerator;

    public StructWriter(final StructGenerator structGenerator) {

        this.structGenerator = structGenerator;
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return Collections.singleton(Struct.class);
    }

    @Override
    public void process(final SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        //iterate fields and generate:
        // -offset
        // -determine correct java mapping for field & generate getter (and setter)
        for (TypeElement typeElement : ElementFilter.typesIn(elementsByAnnotation.values())) {
            final List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();
            parseStructAnnotations(typeElement,
                                   annotationMirrors);
        }
    }

    private void parseStructAnnotations(final TypeElement typeElement,
                                        final List<? extends AnnotationMirror> annotationMirrors) {
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            if (annotationMirror.getAnnotationType()
                                .asElement()
                                .getSimpleName()
                                .toString()
                                .equals(Struct.class.getSimpleName())) {
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues()
                                                                                                               .entrySet()) {
                    if (entry.getKey()
                             .getSimpleName()
                             .toString()
                             .equals("value")) {
                        List<? extends AnnotationValue> values = (List<? extends AnnotationValue>) entry.getValue()
                                                                                                        .getValue();
                        parseStructFields(typeElement,values);
                    }
                    else if (entry.getKey()
                                  .getSimpleName()
                                  .toString()
                                  .equals("union")) {
                        //TODO generate struct with zero offset for all fields if struct is true
                    }
                }
            }
        }

        //TODO stuff
    }

    private void parseStructFields(final TypeElement typeElement,
                                   final List<? extends AnnotationValue> values) {

    }
}
