package com.github.zubnix.jaccall.compiletime;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;

//TODO move to separate jar
public class StructGenerator extends AbstractProcessor {
    @Override
    public boolean process(final Set<? extends TypeElement> annotations,
                           final RoundEnvironment roundEnv) {
        //TODO
        return false;
    }
}
