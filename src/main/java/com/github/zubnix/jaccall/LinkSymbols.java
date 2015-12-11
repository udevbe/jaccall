package com.github.zubnix.jaccall;


public class LinkSymbols {

    private final String[] symbols;
    private final String[] signatures;

    public LinkSymbols(String[] symbols,
                       final String[] signatures) {
        this.symbols = symbols;
        this.signatures = signatures;
    }

    String[] symbols() {
        return this.symbols;
    }

    String[] signatures() {
        return this.signatures;
    }
}
