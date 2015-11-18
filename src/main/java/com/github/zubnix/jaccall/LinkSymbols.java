package com.github.zubnix.jaccall;


public class LinkSymbols {

    private final String[] symbols;
    private final byte[]   argumentSizes;
    private final String[] jaccallSignatures;
    private final String[] jniSignatures;

    public LinkSymbols(final String[] symbols,
                       final byte[] argumentSizes,
                       final String[] jaccallSignatures,
                       final String[] jniSignatures) {
        this.symbols = symbols;
        this.argumentSizes = argumentSizes;
        this.jaccallSignatures = jaccallSignatures;
        this.jniSignatures = jniSignatures;
    }

    String[] symbols() {
        return this.symbols;
    }

    String[] jaccallSignatures() {
        return this.jaccallSignatures;
    }

    String[] jniSignatures() {
        return this.jniSignatures;
    }

    byte[] argumentSizes() { return this.argumentSizes; }
}
