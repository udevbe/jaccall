package com.github.zubnix.jaccall.compiletime;


import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.service.AutoService;

import javax.annotation.processing.Processor;

@AutoService(Processor.class)
public class LinkerGenerator extends BasicAnnotationProcessor {

    @Override
    protected Iterable<? extends ProcessingStep> initSteps() {
        //TODO process @Lib classes

        //TODO check if all native methods have primitive only arguments and return types
        //TODO check if all @Ptr annotations are placed on long return types or arguments
        //TODO check if all ByVal annotations are placed on long return types or arguments

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

        return null;
    }
}
