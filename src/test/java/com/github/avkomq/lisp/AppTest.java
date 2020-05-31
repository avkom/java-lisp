package com.github.avkomq.lisp;

import junit.framework.TestCase;

public class AppTest extends TestCase {

    private void assertReadEvaluatePrint(String input, String expectedOutput) {
        Reader reader = new Reader();
        Evaluator evaluator = new Evaluator();
        Printer printer = new Printer();
        Environment environment = new GlobalEnvironment();

        Object ast = reader.parse(input);
        Object result = evaluator.evaluate(ast, environment);
        String output = printer.print(result);

        assertEquals(expectedOutput, output);
    }

    public void testBegin() {
        assertReadEvaluatePrint("(begin (define a 1.0) (+ a 2.0))", "3.0");
    }

    public void testCallPlus() {
        assertReadEvaluatePrint("(+ 1.0 2.0)", "3.0");
    }

    public void testDefine() {
        assertReadEvaluatePrint("(begin (define a 1) a)", "1");
    }

    public void testEmptyList() {
        assertReadEvaluatePrint("()", "nil");
    }

    public void testIfElseWhenFalse() {
        assertReadEvaluatePrint("(if #f 1 2)", "2");
    }

    public void testIfElseWhenTrue() {
        assertReadEvaluatePrint("(if #t 1 2)", "1");
    }

    public void testIfWhenFalse() {
        assertReadEvaluatePrint("(if #f 1)", "#f");
    }

    public void testIfWhenTrue() {
        assertReadEvaluatePrint("(if #t 1)", "1");
    }

    public void testLambda() {
        assertReadEvaluatePrint("((lambda (a b) (+ a b)) 1.0 2.0)", "3.0");
    }

    public void testQuote() {
        assertReadEvaluatePrint("(quote (a 1))", "(a 1)");
    }
}