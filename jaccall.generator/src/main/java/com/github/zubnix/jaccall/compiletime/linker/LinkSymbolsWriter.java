package com.github.zubnix.jaccall.compiletime.linker;


import com.github.zubnix.jaccall.Lib;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.SetMultimap;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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
        for (Element element : elementsByAnnotation.values()) {
            TypeElement typeElement = (TypeElement) element;

            //TODO if no native methods found, return early

            List<String> methodNames = new LinkedList<>();
            List<Byte> argSizes = new LinkedList<>();
            List<String> jaccallSignatures = new LinkedList<>();
            List<String> jniSignatures = new LinkedList<>();

            for (Element enclosedElement : typeElement.getEnclosedElements()) {
                //TODO gather link symbol information for each native method
                return;
            }

            StringBuilder methodNamesArray = new StringBuilder();
            methodNamesArray.append("new String[]{");
            StringBuilder argSizesArray = new StringBuilder();
            argSizesArray.append("new byte[]{");
            StringBuilder jaccallSignaturesArray = new StringBuilder();
            jaccallSignaturesArray.append("new String[]{");
            StringBuilder jniSignaturesArray = new StringBuilder();
            jniSignaturesArray.append("new String[]{");

            methodNamesArray.append(methodNames.get(0));
            argSizesArray.append(argSizes.get(0));
            jaccallSignaturesArray.append(jaccallSignatures.get(0));
            jniSignaturesArray.append(jniSignatures.get(0));

            for (int i = 1, methodNamesSize = methodNames.size(); i < methodNamesSize; i++) {
                methodNamesArray.append(',')
                                .append(methodNames.get(i));
                argSizesArray.append(',')
                             .append(argSizes.get(i));
                jaccallSignaturesArray.append(',')
                                      .append(jaccallSignatures.get(i));
                jniSignaturesArray.append(',')
                                  .append(jniSignatures.get(i));
            }

            StringBuilder superStatement = new StringBuilder();
            superStatement.append("super(")
                          .append(methodNamesArray.toString())
                          .append(',')
                          .append(argSizesArray.toString())
                          .append(',')
                          .append(jaccallSignaturesArray.toString())
                          .append(',')
                          .append(jniSignaturesArray.toString())
                          .append(")");


            final MethodSpec constructor = MethodSpec.constructorBuilder()
                                                     .addModifiers(Modifier.PUBLIC)
                                                     .addStatement(superStatement.toString())
                                                     .build();


            //TODO create new source file
            //TODO write gathered link symbol information to new source file
            //TODO flush source file
        }
    }
}
