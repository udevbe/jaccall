package com.github.zubnix.jaccall.compiletime.linker;


import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.service.AutoService;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import java.util.Arrays;

@AutoService(Processor.class)
public class LinkerGenerator extends BasicAnnotationProcessor {

    public ProcessingEnvironment getProcessingEnvironment() {
        return this.processingEnv;
    }

    @Override
    protected Iterable<? extends ProcessingStep> initSteps() {

        return Arrays.asList(new CheckWellFormedLib(this),new LinkSymbolsWriter(this));

        //TODO process @Lib classes

        //TODO generate array with native method names

        //TODO generate array with jaccall method signatures
        //C types to Jaccall mapping
//        'c'	char -> Byte, byte
//        's'	short -> Character, char, Short, short
//        'i'	int -> Integer, int
//        'j'	long -> CLong
//        'l'	long long -> Long, long
//        'f'	float -> Float, float
//        'd'	double -> Double, double
//        'p'	C pointer -> @Ptr Long, @Ptr long
//        'v'	void -> Void, void
//        't...]'   struct -> @ByVal(SomeStruct.class) Long, @ByVal(SomeStruct.class) long,

        //TODO generate array with jni method signatures
        //java types to jni mapping
//        'B'	byte
//        'C'	char
//        'S'	short
//        'I'	int
//        'J'	long
//        'F'	float
//        'D'	double

        //TODO generate class that extends LinksSymbols
        //name postfix = "_Jaccall_" + LinkSymbols.class.getSimpleName();
    }

}
