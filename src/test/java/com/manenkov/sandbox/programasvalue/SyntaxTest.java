package com.manenkov.sandbox.programasvalue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test case for {@link Syntax}.
 */
public class SyntaxTest {
    @Test
    public void testSucceed() {
        final String VALUE = "value";
        final Console<String> actual = Syntax.succeed(() -> VALUE);
        final Return<String> expected = Return.of(() -> VALUE);
        assertEquals(expected.value.get(), ((Return<String>) actual).value.get());
    }

    @Test
    public void testPrintLine() {
        final String VALUE = "value";
        final Console<Void> actual = Syntax.printLine(VALUE);
        final PrintLine<Void> expected = PrintLine.of(VALUE, Syntax.succeed(() -> null));
        assertEquals(expected.line, ((PrintLine<Void>) actual).line);
        assertEquals(
                ((Return<Void>) expected.rest).value.get(),
                ((Return<Void>) ((PrintLine<Void>) actual).rest).value.get()
        );
    }

    @Test
    public void testReadLine() {
        final String VALUE = "value";
        final Console<String> actual = Syntax.readLine();
        final ReadLine<String> expected = ReadLine.of(line -> Syntax.succeed(() -> line));
        assertEquals(
                ((Return<String>) expected.rest.apply(VALUE)).value.get(),
                ((Return<String>) ((ReadLine<String>) actual).rest.apply(VALUE)).value.get()
        );
    }
}
