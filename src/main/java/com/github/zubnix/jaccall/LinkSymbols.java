package com.github.zubnix.jaccall;


public class LinkSymbols {

    private final String[] symbols;
    private final String[] jaccallSignatures;
    private final String[] jniSignatures;

    public LinkSymbols(String[] symbols,
                       final String[] jaccallSignatures,
                       final String[] jniSignatures) {
        this.symbols = symbols;
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
}
