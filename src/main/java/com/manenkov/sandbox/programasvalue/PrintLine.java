package com.manenkov.sandbox.programasvalue;

final class PrintLine<A> extends Console<A> {
    public final String line;
    public final Console<A> rest;

    private PrintLine(final String line, final Console<A> rest) {
        this.line = line;
        this.rest = rest;
    }

    static <A> PrintLine<A> of(final String line, final Console<A> rest) {
        return new PrintLine<A>(line, rest);
    }
}
