package com.manenkov.sandbox.programasvalue;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
abstract class Syntax {

    static <T> Console<T> succeed(final Supplier<T> a) {
        return Return.of(a);
    }

    static Console<Void> printLine(final String line) {
        return PrintLine.of(line, succeed(() -> null));
    }

    static Console<String> readLine() {
        return ReadLine.of(line -> succeed(() -> line));
    }
}
