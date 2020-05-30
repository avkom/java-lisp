package com.github.avkomq.lisp;

import java.util.ArrayList;

public class Evaluator {
    private final static Symbol DEFINE = new Symbol("define");

    public Object evaluate(Object ast, Environment environment) {
        if (ast instanceof String ||
            ast instanceof Number ||
            ast instanceof Boolean ||
            ast == null) {
            return ast;
        }

        if (ast instanceof Symbol) {
            return environment.get((Symbol) ast);
        }

        if (ast instanceof ArrayList) {
            ArrayList<Object> list = (ArrayList) ast;
            Object head = list.get(0);

            if (DEFINE.equals(head)) {
                Symbol symbol = (Symbol) list.get(1);
                Object value = evaluate(list.get(2), environment);
                environment.set(symbol, value);
                return value;
            }

            Lambda lambda = (Lambda) evaluate(head, environment);
            ArrayList<Object> args = new ArrayList<>();
            for (int i = 1; i < list.size(); i++) {
                args.add(evaluate(list.get(i), environment));
            }
            return lambda.invoke(args.toArray());
        }

        return null;
    }
}
