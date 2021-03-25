package com.manenkov.sandbox.programasvalue;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.function.Function;

@RequiredArgsConstructor(staticName = "of")
public class Interpreter {

    /**
     * Input stream.
     */
    private final InputStream stdin;

    /**
     * Output stream.
     */
    private final PrintStream stdout;

    /**
     * Error stream.
     */
    private final PrintStream stderr;

    protected <A> A interpret(final Console<A> program) {
        if (program instanceof Return) {
            return ((Return<A>) program).value.get();
        } else if (program instanceof PrintLine) {
            final String line = ((PrintLine<A>) program).line;
            final Console<A> next = ((PrintLine<A>) program).rest;
            stdout.println(line);
            return interpret(next);
        } else if (program instanceof ReadLine) {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(stdin));
            final Function<String, Console<A>> next = ((ReadLine<A>) program).rest;
            try {
                return interpret(next.apply(reader.readLine()));
            } catch (final IOException e) {
                e.printStackTrace(stderr);
            }
        }
        return null;
    }
}
