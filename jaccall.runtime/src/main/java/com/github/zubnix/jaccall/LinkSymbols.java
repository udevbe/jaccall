package com.github.zubnix.jaccall;


public class LinkSymbols {

    private final String[] symbols;
    private final byte[]   argumentSizes;
    private final long[][] ffiTypes;
    private final String[] jniSignatures;

    public LinkSymbols(final String[] symbols,
                       final byte[] argumentSizes,
                       final long[][] ffiTypes,
                       final String[] jniSignatures) {
        this.symbols = symbols;
        this.argumentSizes = argumentSizes;
        this.ffiTypes = ffiTypes;
        this.jniSignatures = jniSignatures;
    }

    String[] symbols() {
        return this.symbols;
    }

    long[][] ffiTypes() {
        return this.ffiTypes;
    }

    String[] jniSignatures() {
        return this.jniSignatures;
    }

    byte[] argumentSizes() { return this.argumentSizes; }
}
