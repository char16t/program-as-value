package com.manenkov.sandbox.programasvalue;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
final class PrintLine<A> extends Console<A> {
    public final String line;
    public final Console<A> rest;
}
