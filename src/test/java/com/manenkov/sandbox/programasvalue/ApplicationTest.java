package com.manenkov.sandbox.programasvalue;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static com.manenkov.sandbox.programasvalue.Syntax.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test case for {@link Application}.
 */
public class ApplicationTest {
    @Test
    public void testExample() throws IOException {
        final String userInput = "Valeriy";
        final String expected =
                "Hello! What's your name?" + System.lineSeparator()
                        + "Good to meet you, " + userInput + System.lineSeparator();
        try (final ByteArrayInputStream stdin = new ByteArrayInputStream(userInput.getBytes())) {
            try (final ByteArrayOutputStream stdout = new ByteArrayOutputStream()) {
                final Console<Void> program =
                        PrintLine.of("Hello! What's your name?",
                                ReadLine.of(name ->
                                        PrintLine.of("Good to meet you, " + name, Return.of(() -> null))));
                final Interpreter interpreter = Interpreter.of(stdin, new PrintStream(stdout), System.err);
                new Application<>(program, interpreter).exec();
                assertEquals(expected, stdout.toString());
            }
        }
    }

    @Test
    public void testExec() throws IOException {
        final String userInput = "Valeriy";
        final String expected =
                "Hello! What's your name?" + System.lineSeparator()
                        + "Hello, " + userInput + System.lineSeparator();
        try (final ByteArrayInputStream stdin = new ByteArrayInputStream(userInput.getBytes())) {
            try (final ByteArrayOutputStream stdout = new ByteArrayOutputStream()) {
                final Console<String> program = Application.program();
                final Interpreter interpreter = Interpreter.of(stdin, new PrintStream(stdout), System.err);
                new Application<>(program, interpreter).exec();
                assertEquals(expected, stdout.toString());
            }
        }
    }
}
