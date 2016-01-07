package com.github.zubnix.jaccall.compiletime.struct;


public class FieldDefinition {


    private final String   ffiTypeCode;
    private final Object[] ffiTypeCodeArgs;
    private final String offsetCode;
    private final Object[] offsetCodeArgs;
    private final String   sizeOfCode;
    private final Object[] sizeOfCodeArgs;

    public FieldDefinition(final String ffiTypeCode,
                           final Object[] ffiTypeCodeArgs,
                           final String offsetCode,
                           final Object[] offsetCodeArgs,
                           final String sizeOfCode,
                           final Object[] sizeOfCodeArgs) {
        this.ffiTypeCode = ffiTypeCode;
        this.ffiTypeCodeArgs = ffiTypeCodeArgs;
        this.offsetCode = offsetCode;
        this.offsetCodeArgs = offsetCodeArgs;
        this.sizeOfCode = sizeOfCode;
        this.sizeOfCodeArgs = sizeOfCodeArgs;
    }

    public String getFfiTypeCode() {
        return this.ffiTypeCode;
    }

    public String getOffsetCode() {
        return this.offsetCode;
    }

    public Object[] getOffsetCodeArgs() {
        return this.offsetCodeArgs;
    }

    public Object[] getFfiTypeCodeArgs() {
        return this.ffiTypeCodeArgs;
    }

    public String getSizeOfCode() {
        return this.sizeOfCode;
    }

    public Object[] getSizeOfCodeArgs() {
        return this.sizeOfCodeArgs;
    }
}
