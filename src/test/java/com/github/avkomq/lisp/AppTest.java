package com.github.avkomq.lisp;

import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    private <T extends Exception> void assertReadEvaluatePrintThrows(String input, Class<T> expectedType, String expectedMessage) {
        Exception exception = assertThrows(expectedType, () -> {
            Reader reader = new Reader();
            Evaluator evaluator = new Evaluator();
            Printer printer = new Printer();
            Environment environment = new GlobalEnvironment();

            Object ast = reader.parse(input);
            Object result = evaluator.evaluate(ast, environment);
            printer.print(result);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }

    public void testBegin() {
        assertReadEvaluatePrint("(begin (define a 1.0) (+ a 2.0))", "3.0");
    }

    public void testCallPlus() {
        assertReadEvaluatePrint("(+ 1.0 2.0)", "3.0");
    }

    public void testCallLambdaVariadicWithoutRestA() {
        assertReadEvaluatePrint("((lambda (a . b) a) 1)", "1");
    }

    public void testCallLambdaVariadicWithoutRestB() {
        assertReadEvaluatePrint("((lambda (a . b) b) 1)", "()");
    }

    public void testCallLambdaVariadicWithRestA() {
        assertReadEvaluatePrint("((lambda (a . b) a) 1 2 3)", "1");
    }

    public void testCallLambdaVariadicWithRestB() {
        assertReadEvaluatePrint("((lambda (a . b) b) 1 2 3)", "(2 3)");
    }

    public void testCallWhenValueIsNotFunction() {
        assertReadEvaluatePrintThrows("(123)", SyntaxErrorException.class,
                "The value is not a function");
    }

    public void testCallLambdaWithFewArguments() {
        assertReadEvaluatePrintThrows("((lambda (a b) (+ a b)) 1)", SyntaxErrorException.class,
                "Few arguments passed to the function call");
    }

    public void testCallLambdaVariadicWithFewArguments() {
        assertReadEvaluatePrintThrows("((lambda (a  b . c) a) 1)", SyntaxErrorException.class,
                "Few arguments passed to the function call");
    }

    public void testDefine() {
        assertReadEvaluatePrint("(begin (define a 1) a)", "1");
    }

    public void testDefineWhenSymbolArgumentMissing() {
        assertReadEvaluatePrintThrows("(define)", SyntaxErrorException.class,
                "The first (Symbol) argument is missing in 'define' special form");
    }

    public void testDefineWhenFirstArgumentIsNotSymbol() {
        assertReadEvaluatePrintThrows("(define 1)", SyntaxErrorException.class,
                "The first argument of 'define' special form is not a Symbol");
    }

    public void testDefineWhenValueArgumentMissing() {
        assertReadEvaluatePrintThrows("(define a)", SyntaxErrorException.class,
                "The second (value) argument missing in 'define' special form");
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

    public void testIfWhenConditionArgumentMissing() {
        assertReadEvaluatePrintThrows("(if)", SyntaxErrorException.class,
                "The first (condition) argument is missing in 'if' special form");
    }

    public void testIfWhenSecondArgumentMissing() {
        assertReadEvaluatePrintThrows("(if true)", SyntaxErrorException.class,
                "The second (true path) argument is missing in 'if' special form");
    }

    public void testLambda() {
        assertReadEvaluatePrint("((lambda (a b) (+ a b)) 1.0 2.0)", "3.0");
    }

    public void testLambdaWhenParameterNamesArgumentMissing() {
        assertReadEvaluatePrintThrows("(lambda)", SyntaxErrorException.class,
                "The first argument (parameter names) of 'lambda' special form is missing");
    }

    public void testLambdaWhenBodyArgumentMissing() {
        assertReadEvaluatePrintThrows("(lambda (a b))", SyntaxErrorException.class,
                "The second argument (body) of 'lambda' special form is missing");
    }

    public void testLambdaWhenFirstArgumentIsNotList() {
        assertReadEvaluatePrintThrows("(lambda 123 456)", SyntaxErrorException.class,
                "The first argument of 'lambda' special form is not a list of parameter names");
    }

    public void testLambdaWhenParameterNameIsNotSymbol() {
        assertReadEvaluatePrintThrows("(lambda (1) 2)", SyntaxErrorException.class,
                "The parameter of 'lambda' special form is not a Symbol");
    }

    public void testQuote() {
        assertReadEvaluatePrint("(quote (a 1))", "(a 1)");
    }

    public void testQuoteWhenArgumentMissing() {
        assertReadEvaluatePrintThrows("(quote)", SyntaxErrorException.class,
                "The argument of 'quote' special form is missing");
    }

    public void testPrintLambda() {
        assertReadEvaluatePrint("((lambda (x y) (lambda (a b) (+ a b))) 1 2)", "(lambda (a b) (+ a b) (x y))");
    }
}