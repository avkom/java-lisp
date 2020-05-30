package com.github.avkomq.lisp;

import java.util.ArrayList;

public class Evaluator {
    private final static Symbol BEGIN = new Symbol("begin");
    private final static Symbol DEFINE = new Symbol("define");
    private final static Symbol LAMBDA = new Symbol("lambda");

    public Object evaluate(Object ast, Environment environment) {

        if (ast instanceof Symbol) {
            return environment.get((Symbol) ast);
        }

        if (ast instanceof ArrayList) {
            ArrayList<Object> list = (ArrayList<Object>) ast;
            Object head = list.get(0);

            if (BEGIN.equals(head)) {
                Object value = null;

                for (int i = 1; i < list.size(); i++) {
                    value = evaluate(list.get(i), environment);
                }

                return value;
            }

            if (DEFINE.equals(head)) {
                Symbol symbol = (Symbol) list.get(1);
                Object value = evaluate(list.get(2), environment);
                environment.set(symbol, value);
                return value;
            }

            if (LAMBDA.equals(head)) {
                return new Closure((ArrayList<Symbol>) list.get(1), list.get(2), environment, this);
            }

            // Function call
            Lambda lambda = (Lambda) evaluate(head, environment);
            ArrayList<Object> args = new ArrayList<>(list.size() - 1);
            for (int i = 1; i < list.size(); i++) {
                args.add(evaluate(list.get(i), environment));
            }
            return lambda.invoke(args.toArray());
        }

        return ast;
    }
}
