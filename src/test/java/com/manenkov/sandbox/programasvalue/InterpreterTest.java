package com.manenkov.sandbox.programasvalue;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test case for {@link Interpreter}.
 */
public class InterpreterTest {
    @Test
    public void testInterpretReturn() {
        final Interpreter interpreter = Interpreter.of(System.in, System.out, System.err);
        final String expected = "value";
        final String actual = interpreter.interpret(Return.of(() -> "value"));
        assertEquals(expected, actual);
    }

    @Test
    public void testInterpretPrintLine() throws IOException {
        final String expected = "content to print";
        try (final ByteArrayOutputStream stdout = new ByteArrayOutputStream()) {
            final Interpreter interpreter = Interpreter.of(System.in, new PrintStream(stdout), System.err);
            interpreter.interpret(PrintLine.of(expected, Return.of(() -> null)));
            assertEquals(expected + System.lineSeparator(), stdout.toString());
        }
    }

    @Test
    public void testInterpretReadLine() throws IOException {
        final String expected = "user input";
        try (final ByteArrayInputStream stdin = new ByteArrayInputStream(expected.getBytes())) {
            final Interpreter interpreter = Interpreter.of(stdin, System.out, System.err);
            final String actual = interpreter.interpret(ReadLine.of(value -> Return.of(() -> value)));
            assertEquals(expected, actual);
        }
    }
}
