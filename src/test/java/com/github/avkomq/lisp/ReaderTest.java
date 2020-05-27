package com.github.avkomq.lisp;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;

public class ReaderTest extends TestCase {
    public void testGetTokens() {
        // Arrange
        Reader reader = new Reader();
        String input = "(-1.1e2 2 +Inf -Inf NaN \"Hello, \\World!\" true false null)";

        // Act
        ArrayList<String> tokens = reader.getTokens(input);

        // Assert
        System.out.println(Arrays.toString(tokens.toArray()));
        assertEquals(11, tokens.size());
        assertEquals("(", tokens.get(0));
        assertEquals("-1.1e2", tokens.get(1));
        assertEquals("2", tokens.get(2));
        assertEquals("+Inf", tokens.get(3));
        assertEquals("-Inf", tokens.get(4));
        assertEquals("NaN", tokens.get(5));
        assertEquals("\"Hello, \\World!\"", tokens.get(6));
        assertEquals("true", tokens.get(7));
        assertEquals("false", tokens.get(8));
        assertEquals("null", tokens.get(9));
        assertEquals(")", tokens.get(10));
    }
}
