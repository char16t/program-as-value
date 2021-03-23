package com.manenkov.sandbox.programasvalue;

import java.util.function.Supplier;

final class Return<A> extends Console<A> {
    public final Supplier<A> value;

    private Return(final Supplier<A> value) {
        super();
        this.value = value;
    }

    static <A> Return<A> of(final Supplier<A> value) {
        return new Return<A>(value);
    }
}
