package com.github.zubnix.jaccall;


public class LinkSymbols {

    private final String[] symbols;
    private final byte[]   argumentSizes;
    private final long[]   ffiCallInterfaces;
    private final String[] jniSignatures;

    public LinkSymbols(final String[] symbols,
                       final byte[] argumentSizes,
                       final long[] ffiCallInterfaces,
                       final String[] jniSignatures) {
        this.symbols = symbols;
        this.argumentSizes = argumentSizes;
        this.ffiCallInterfaces = ffiCallInterfaces;
        this.jniSignatures = jniSignatures;
    }

    String[] symbols() {
        return this.symbols;
    }

    long[] ffiCallInterfaces() {
        return this.ffiCallInterfaces;
    }

    String[] jniSignatures() {
        return this.jniSignatures;
    }

    byte[] argumentSizes() {
        return this.argumentSizes;
    }
}
