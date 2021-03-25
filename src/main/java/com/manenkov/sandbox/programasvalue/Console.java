package com.manenkov.sandbox.programasvalue;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.function.Function;
import java.util.function.Supplier;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
abstract class Console<A> {

    <B> Console<B> map(final Function<A, B> f) {
        return flatMap(a -> Syntax.succeed(() -> f.apply(a)));
    }

    <B> Console<B> flatMap(final Function<A, Console<B>> f) {
        if (this instanceof Return) {
            final Supplier<A> value = ((Return<A>) this).value;
            return f.apply(value.get());
        } else if (this instanceof PrintLine) {
            final String line = ((PrintLine<A>) this).line;
            final Console<A> next = ((PrintLine<A>) this).rest;
            return PrintLine.of(line, next.flatMap(f));
        } else if (this instanceof ReadLine) {
            final Function<String, Console<A>> next = ((ReadLine<A>) this).rest;
            return ReadLine.of(line -> next.apply(line).flatMap(f));
        }
        return null;
    }
}
