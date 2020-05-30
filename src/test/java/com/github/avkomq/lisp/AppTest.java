package com.github.avkomq.lisp;

import junit.framework.TestCase;

public class AppTest extends TestCase {

    private void assertReadEvaluatePrint(String input, String expectedOutput) {
        Reader reader = new Reader();
        Evaluator evaluator = new Evaluator();
        Printer printer = new Printer();
        Environment globalEnvironment = new GlobalEnvironment();

        Object ast = reader.parse(input);
        Object result = evaluator.evaluate(ast, globalEnvironment);
        String output = printer.print(result);

        assertEquals(expectedOutput, output);
    }

    public void testPlusDouble() {
        assertReadEvaluatePrint("(+ 1.0 2.0)", "3.0");
    }

    public void testLambda() {
        assertReadEvaluatePrint("((lambda (a b) (+ a b)) 1.0 2.0)", "3.0");
    }

    public void testBegin() {
        assertReadEvaluatePrint("(begin (define a 1.0) (+ a 2.0))", "3.0");
    }
}