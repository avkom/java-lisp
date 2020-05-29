package com.github.avkomq.lisp;

import junit.framework.TestCase;

import java.util.ArrayList;

public class PrinterTest extends TestCase {

    public void testPrint() {
        // Arrange
        Printer printer = new Printer();
        ArrayList<Object> ast = new ArrayList<>();
        ast.add(-1.1);
        ast.add(2);
        ast.add(Double.POSITIVE_INFINITY);
        ast.add(Double.NEGATIVE_INFINITY);
        ast.add(Double.NaN);
        ast.add("Hello, \\World!");
        ast.add(new Symbol("-a.b?"));
        ast.add(true);
        ast.add(false);
        ast.add(null);

        // Act
        String output = printer.print(ast);

        // Assert
        assertEquals("(-1.1 2 Inf -Inf NaN \"Hello, \\World!\" -a.b? #t #f nil)", output);
    }
}