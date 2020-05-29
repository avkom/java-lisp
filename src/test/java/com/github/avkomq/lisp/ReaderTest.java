package com.github.avkomq.lisp;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;

public class ReaderTest extends TestCase {
    public void testGetTokens() {
        // Arrange
        Reader reader = new Reader();
        String input = "(-1.1e2 2 +Inf -Inf NaN \"Hello, \\World!\" -a.b? #t #f nil)";

        // Act
        ArrayList<String> tokens = reader.getTokens(input);

        // Assert
        System.out.println(Arrays.toString(tokens.toArray()));
        assertEquals(12, tokens.size());
        assertEquals("(", tokens.get(0));
        assertEquals("-1.1e2", tokens.get(1));
        assertEquals("2", tokens.get(2));
        assertEquals("+Inf", tokens.get(3));
        assertEquals("-Inf", tokens.get(4));
        assertEquals("NaN", tokens.get(5));
        assertEquals("\"Hello, \\World!\"", tokens.get(6));
        assertEquals("-a.b?", tokens.get(7));
        assertEquals("#t", tokens.get(8));
        assertEquals("#f", tokens.get(9));
        assertEquals("nil", tokens.get(10));
        assertEquals(")", tokens.get(11));
    }

    public void testGetAst() {
        // Arrange
        Reader reader = new Reader();
        String[] tokens = new String[] {
                "(", "-1.1e2", "2", "Inf", "-Inf", "NaN", "\"Hello, \\World!\"", "-a.b?", "#t", "#f", "nil", ")"
        };
        Enumerator<String> enumerator = new Enumerator<>(Arrays.asList(tokens));
        enumerator.moveNext();

        // Act
        Object ast = reader.getAst(enumerator);

        // Assert
        ArrayList<Object> list = (ArrayList<Object>) ast;
        System.out.println(Arrays.toString(list.toArray()));

        assertEquals(10, list.size());
        assertEquals(-1.1e2, (double) list.get(0));
        assertEquals(2, (int) list.get(1));
        assertEquals(Double.POSITIVE_INFINITY, (double) list.get(2));
        assertEquals(Double.NEGATIVE_INFINITY, (double) list.get(3));
        assertEquals(Double.NaN, (double) list.get(4));
        assertEquals("Hello, \\World!", (String) list.get(5));
        assertEquals(new Symbol("-a.b?"), (Symbol) list.get(6));
        assertEquals(true, (boolean) list.get(7));
        assertEquals(false, (boolean) list.get(8));
        assertNull(list.get(9));
    }

    public void testGetAstNested() {
        // Arrange
        Reader reader = new Reader();
        String[] tokens = new String[] {
                "(", "1", "(", "2", "3", ")", "\"Hello\"", ")"
        };
        Enumerator<String> enumerator = new Enumerator<>(Arrays.asList(tokens));
        enumerator.moveNext();

        // Act
        Object ast = reader.getAst(enumerator);

        // Assert
        ArrayList<Object> list = (ArrayList<Object>) ast;
        System.out.println(Arrays.toString(list.toArray()));

        assertEquals(3, list.size());
        assertEquals(1, (int) list.get(0));
        assertEquals("Hello", (String) list.get(2));

        ArrayList<Object> nestedList = (ArrayList<Object>) list.get(1);
        assertEquals(2, nestedList.size());
        assertEquals(2, (int) nestedList.get(0));
        assertEquals(3, (int) nestedList.get(1));
    }
}
