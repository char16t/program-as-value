package com.manenkov.sandbox.programasvalue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test case for {@link Return}.
 */
public class ReturnTest {
    @Test
    public void testInit() {
        final String VALUE = "value";
        final Return<String> ret = Return.of(() -> VALUE);
        assertEquals(VALUE, ret.value.get());
    }
}
