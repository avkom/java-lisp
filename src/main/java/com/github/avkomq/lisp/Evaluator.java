package com.github.avkomq.lisp;

import java.util.ArrayList;

public class Evaluator {
    private final static Symbol BEGIN = new Symbol("begin");
    private final static Symbol DEFINE = new Symbol("define");
    private final static Symbol IF = new Symbol("if");
    private final static Symbol LAMBDA = new Symbol("lambda");
    private final static Symbol QUOTE = new Symbol("quote");

    public Object evaluate(Object ast, Environment environment) {

        if (ast instanceof Symbol) {
            return environment.get((Symbol) ast);
        }

        if (ast instanceof ArrayList) {
            ArrayList<Object> list = (ArrayList<Object>) ast;

            if (list.size() == 0) {
                return null;
            }

            Object head = list.get(0);

            if (BEGIN.equals(head)) {
                Object value = null;

                for (int i = 1; i < list.size(); i++) {
                    value = evaluate(list.get(i), environment);
                }

                return value;
            }

            if (DEFINE.equals(head)) {
                if (list.size() < 2) {
                    throw new SyntaxErrorException("The first (Symbol) argument is missing in 'define' special form");
                }

                if (!(list.get(1) instanceof Symbol)) {
                    throw new SyntaxErrorException("The first argument of 'define' special form is not a Symbol");
                }

                if (list.size() < 3) {
                    throw new SyntaxErrorException("The second (value) argument missing in 'define' special form");
                }

                Symbol symbol = (Symbol) list.get(1);
                Object value = evaluate(list.get(2), environment);
                environment.set(symbol, value);
                return value;
            }

            if (IF.equals(head)) {
                if (list.size() < 2) {
                    throw new SyntaxErrorException("The first (condition) argument is missing in 'if' special form");
                }

                if (list.size() < 3) {
                    throw new SyntaxErrorException("The second (true path) argument is missing in 'if' special form");
                }

                Object condition = evaluate(list.get(1), environment);
                if (condition == Boolean.TRUE) {
                    return evaluate(list.get(2), environment);
                }
                else {
                    if (list.size() == 4) {
                        return evaluate(list.get(3), environment);
                    }
                    else {
                        return condition;
                    }
                }
            }

            if (LAMBDA.equals(head)) {
                if (list.size() < 2) {
                    throw new SyntaxErrorException("The first argument (parameter names) of 'lambda' special form is missing");
                }

                if (list.size() < 3) {
                    throw new SyntaxErrorException("The second argument (body) of 'lambda' special form is missing");
                }

                if (!(list.get(1) instanceof ArrayList)) {
                    throw new SyntaxErrorException("The first argument of 'lambda' special form is not a list of parameter names");
                }

                ArrayList<Symbol> parameters = (ArrayList<Symbol>) list.get(1);
                for (Object parameter: parameters) {
                    if (!(parameter instanceof Symbol)) {
                        throw new SyntaxErrorException("The parameter of 'lambda' special form is not a Symbol");
                    }
                }

                return new Closure(parameters, list.get(2), environment, this);
            }

            if (QUOTE.equals(head)) {
                if (list.size() < 2) {
                    throw new SyntaxErrorException("The argument of 'quote' special form is missing");
                }

                return list.get(1);
            }

            // Function call
            Object function = evaluate(head, environment);
            if (!(function instanceof Lambda)) {
                throw new SyntaxErrorException("The value is not a function");
            }
            Lambda lambda = (Lambda) function;

            ArrayList<Object> args = new ArrayList<>(list.size() - 1);
            for (int i = 1; i < list.size(); i++) {
                args.add(evaluate(list.get(i), environment));
            }
            
            return lambda.invoke(args.toArray());
        }

        return ast;
    }
}
