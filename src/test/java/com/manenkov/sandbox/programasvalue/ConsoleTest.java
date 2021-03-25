package com.manenkov.sandbox.programasvalue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test case for {@link Console}.
 */
public class ConsoleTest {
    @Test
    public void testMapReturn() {
        final Return<String> before = Return.of(() -> "value");
        final Console<String> after = before.map(value -> "modified" + value);
        assertEquals("modified" + before.value.get(), ((Return<String>) after).value.get());
    }

    @Test
    public void testMapPrintLine() {
        final PrintLine<String> before = PrintLine.of("line", Return.of(() -> "ret"));
        final Console<String> after = before.map(value -> "modified" + value);
        assertEquals(before.line, ((PrintLine<String>) after).line);
        assertEquals(
                "modified" + ((Return<String>) before.rest).value.get(),
                ((Return<String>) ((PrintLine<String>) after).rest).value.get()
        );
    }

    @Test
    public void testMapReadLine() {
        final ReadLine<String> before = ReadLine.of(line -> Return.of(() -> line));
        final Console<String> after = before.map(value -> "modified" + value);
        assertEquals(
                "modified" + ((Return<String>) before.rest.apply("LINE")).value.get(),
                ((Return<String>) ((ReadLine<String>) after).rest.apply("LINE")).value.get()
        );
    }

    @Test
    public void testFlatMapReturn() {
        final Return<String> before = Return.of(() -> "value");
        final Console<String> after = before.flatMap(value -> Return.of(() -> "modified" + value));
        assertEquals("modified" + before.value.get(), ((Return<String>) after).value.get());
    }

    @Test
    public void testFlatMapPrintLine() {
        final PrintLine<String> before = PrintLine.of("line", Return.of(() -> "ret"));
        final Console<String> after = before.flatMap(value -> PrintLine.of("line", Return.of(() -> "modified" + value)));
        assertEquals(before.line, ((PrintLine<String>) after).line);
        assertEquals(
                "modified" + ((Return<String>) before.rest).value.get(),
                ((Return<String>) ((PrintLine<String>) ((PrintLine<String>) after).rest).rest).value.get()
        );
    }

    @Test
    public void testFlatMapReadLine() {
        final ReadLine<String> before = ReadLine.of(line -> Return.of(() -> line));
        final Console<String> after = before.flatMap(value -> ReadLine.of(line -> Return.of(() -> "modified" + line)));
        assertEquals(
                "modified" + ((Return<String>) before.rest.apply("LINE")).value.get(),
                ((Return<String>) ((ReadLine<String>) ((ReadLine<String>) after).rest.apply("LINE")).rest.apply("LINE")).value.get()
        );
    }
}
