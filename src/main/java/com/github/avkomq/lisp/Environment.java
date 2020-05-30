package com.github.avkomq.lisp;

import java.util.HashMap;

public class Environment {
    private Environment outer;
    private HashMap<Symbol, Object> map = new HashMap<>();

    public Environment(Environment outer) {
        this.outer = outer;
    }

    public Object get(Symbol symbol) {
        Object value = null;

        if (map.containsKey(symbol)) {
            value = map.get(symbol);
        }
        else {
            if (outer != null) {
                return outer.get(symbol);
            }
        }

        return value;
    }

    public void set(Symbol symbol, Object value) {
        map.put(symbol, value);
    }
}
