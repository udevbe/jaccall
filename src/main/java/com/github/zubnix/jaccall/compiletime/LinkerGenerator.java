package com.github.zubnix.jaccall.compiletime;


import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;

public class LinkerGenerator extends AbstractProcessor {
    //C to Java mapping
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

    @Override
    public boolean process(final Set<? extends TypeElement> annotations,
                           final RoundEnvironment roundEnv) {
        //TODO
        return false;
    }
}
