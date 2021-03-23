package com.manenkov.sandbox.programasvalue;

import java.util.function.Function;

final class ReadLine<A> extends Console<A> {
    public final Function<String, Console<A>> rest;

    private ReadLine(final Function<String, Console<A>> rest) {
        this.rest = rest;
    }

    static <A> ReadLine<A> of(final Function<String, Console<A>> rest) {
        return new ReadLine<A>(rest);
    }
}
