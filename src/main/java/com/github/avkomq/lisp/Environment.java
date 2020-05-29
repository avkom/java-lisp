package com.github.avkomq.lisp;

import java.util.HashMap;

public class Environment {
    private HashMap<Symbol, Object> map = new HashMap<>();

    public Object get(Symbol symbol) {
        return map.get(symbol);
    }

    public void set(Symbol symbol, Object value) {
        map.put(symbol, value);
    }
}
