package com.github.avkomq.lisp;

public class GlobalEnvironment extends Environment {

    public GlobalEnvironment() {
        super(null);
        addLambda("+", args -> (Double)args[0] + (Double)args[1]);
    }

    private void addLambda(String symbol, Lambda lambda) {
        set(new Symbol(symbol), lambda);
    }
}
