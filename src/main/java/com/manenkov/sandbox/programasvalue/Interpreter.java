package com.manenkov.sandbox.programasvalue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;

public class Interpreter {
    protected <A> A interpret(final Console<A> program) {
        if (program instanceof Return) {
            return ((Return<A>) program).value.get();
        } else if (program instanceof PrintLine) {
            final String line = ((PrintLine<A>) program).line;
            final Console<A> next = ((PrintLine<A>) program).rest;
            System.out.println(line);
            return interpret(next);
        } else if (program instanceof ReadLine) {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            final Function<String, Console<A>> next = ((ReadLine<A>) program).rest;
            try {
                return interpret(next.apply(reader.readLine()));
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
