package com.github.avkomq.lisp;

public class Closure implements Lambda {
    private String[] parameters;
    private Object body;
    private Environment environment;

    public Closure(String[] parameters, Object body, Environment environment) {
        this.parameters = parameters;
        this.body = body;
        this.environment = environment;
    }

    @Override
    public Object invoke(Object[] args) {
        return null;
    }
}
