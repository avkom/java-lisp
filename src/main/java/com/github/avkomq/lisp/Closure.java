package com.github.avkomq.lisp;

import java.util.ArrayList;

public class Closure implements Lambda {
    private final static Symbol VARIADIC = new Symbol(".");

    private final ArrayList<Symbol> parameters;
    private final Object body;
    private final Environment environment;
    private final Evaluator evaluator;

    public Closure(ArrayList<Symbol> parameters, Object body, Environment environment, Evaluator evaluator) {
        this.parameters = parameters;
        this.body = body;
        this.environment = environment;
        this.evaluator = evaluator;
    }

    public ArrayList<Symbol> getParameters() {
        return parameters;
    }

    public Object getBody() {
        return body;
    }

    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public Object invoke(Object[] args) {
        Environment environment = new Environment(this.environment);

        if (parameters.size() > 1 && VARIADIC.equals(parameters.get(parameters.size() - 2))) {
            if (args.length < parameters.size() - 2) {
                throw new SyntaxErrorException("Few arguments passed to the function call");
            }

            for (int i = 0; i < parameters.size() - 2; i++) {
                environment.set(parameters.get(i), args[i]);
            }

            ArrayList<Object> restArgs = new ArrayList<>();
            for (int i = parameters.size() - 2; i < args.length; i++) {
                restArgs.add(args[i]);
            }

            environment.set(parameters.get(parameters.size() - 1), restArgs);
        }
        else {
            if (args.length < parameters.size()) {
                throw new SyntaxErrorException("Few arguments passed to the function call");
            }

            for (int i = 0; i < parameters.size(); i++) {
                environment.set(parameters.get(i), args[i]);
            }
        }

        return evaluator.evaluate(this.body, environment);
    }
}
