package com.manenkov.sandbox.programasvalue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test case for {@link PrintLine}.
 */
public class PrintLineTest {
    @Test
    public void testInit() {
        final String VALUE = "value";
        final String LINE = "line";
        final PrintLine<String> pl = PrintLine.of(LINE, Return.of(() -> VALUE));
        assertEquals(LINE, pl.line);
        assertEquals(VALUE, ((Return<String>) pl.rest).value.get());
    }
}
