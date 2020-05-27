package com.github.avkomq.lisp;

import junit.framework.TestCase;

import java.util.ArrayList;

public class ReaderTest extends TestCase {
    public void testGetTokens() {
        // Arrange
        Reader reader = new Reader();
        String input = "(-1.1e2 2 3)";

        // Act
        ArrayList<String> tokens = reader.getTokens(input);

        // Assert
        assertEquals(5, tokens.size());
        assertEquals("(", tokens.get(0));
        assertEquals("-1.1e2", tokens.get(1));
        assertEquals("2", tokens.get(2));
        assertEquals("3", tokens.get(3));
        assertEquals(")", tokens.get(4));
    }
}
