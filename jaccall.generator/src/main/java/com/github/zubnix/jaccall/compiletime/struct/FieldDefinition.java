package com.github.zubnix.jaccall.compiletime.struct;


import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import java.util.List;

public class FieldDefinition {


    private final CodeBlock        ffiTypeCode;
    private final CodeBlock        offsetCode;
    private final CodeBlock        sizeOfCode;
    private final int              cardinality;
    private final List<MethodSpec> accessorsCode;

    public FieldDefinition(final CodeBlock ffiTypeCode,
                           final CodeBlock offsetCode,
                           final CodeBlock sizeOfCode,
                           final int cardinality,
                           final List<MethodSpec> accessorsCode) {
        this.ffiTypeCode = ffiTypeCode;
        this.offsetCode = offsetCode;
        this.sizeOfCode = sizeOfCode;
        this.cardinality = cardinality;
        this.accessorsCode = accessorsCode;
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

    public List<MethodSpec> getAccessorsCode() {
        return this.accessorsCode;
    }
}
