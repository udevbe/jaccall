package com.github.zubnix.jaccall.compiletime.struct;


import com.squareup.javapoet.CodeBlock;

public class FieldDefinition {


    private final CodeBlock ffiTypeCode;
    private final CodeBlock offsetCode;
    private final CodeBlock sizeOfCode;
    private final int       cardinality;

    public FieldDefinition(final CodeBlock ffiTypeCode,
                           final CodeBlock offsetCode,
                           final CodeBlock sizeOfCode,
                           final int cardinality) {
        this.ffiTypeCode = ffiTypeCode;
        this.offsetCode = offsetCode;
        this.sizeOfCode = sizeOfCode;
        this.cardinality = cardinality;
    }

    public CodeBlock getFfiTypeCode() {
        return this.ffiTypeCode;
    }

    public CodeBlock getOffsetCode() {
        return this.offsetCode;
    }

    public CodeBlock getSizeOfCode() {
        return this.sizeOfCode;
    }

    public int getCardinality() {
        return this.cardinality;
    }
}
