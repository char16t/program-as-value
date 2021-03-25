package com.manenkov.sandbox.programasvalue;

import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "of")
final class ReadLine<A> extends Console<A> {
    public final Function<String, Console<A>> rest;
}
