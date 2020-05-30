package com.github.avkomq.lisp;

import java.util.ArrayList;

public class Closure implements Lambda {
    private ArrayList<Symbol> parameters;
    private Object body;
    private Environment environment;
    private Evaluator evaluator;

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
        for (int i = 0; i < parameters.size(); i++) {
            environment.set(parameters.get(i), args[i]);
        }

        return evaluator.evaluate(this.body, environment);
    }
}
