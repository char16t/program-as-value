package com.manenkov.sandbox.programasvalue;

import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor(staticName = "of")
final class Return<A> extends Console<A> {
    public final Supplier<A> value;
}
