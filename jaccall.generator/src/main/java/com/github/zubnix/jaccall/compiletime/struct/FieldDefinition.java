package com.github.zubnix.jaccall.compiletime.struct;


public class FieldDefinition {


    private final String ffiTypeCode;
    private final String offsetCode;
    private final String sizeOfCode;

    public FieldDefinition(final String ffiTypeCode,
                           final String offsetCode,
                           final String sizeOfCode) {
        this.ffiTypeCode = ffiTypeCode;
        this.offsetCode = offsetCode;
        this.sizeOfCode = sizeOfCode;
    }

    public String getFfiTypeCode() {
        return this.ffiTypeCode;
    }

    public String getOffsetCode() {
        return this.offsetCode;
    }

    public String sizeOfCode(){
        return this.sizeOfCode;
    }
}
