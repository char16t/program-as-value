package com.manenkov.sandbox.programasvalue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test case for {@link ReadLine}.
 */
public class ReadLineTest {
    @Test
    public void testInit() {
        final String LINE = "line";
        final ReadLine<String> rl = ReadLine.of(readValue -> Return.of(() -> readValue));
        assertEquals(LINE, ((Return<String>) rl.rest.apply(LINE)).value.get());
    }
}
