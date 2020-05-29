package com.github.avkomq.lisp;

public class Symbol {
    private String value;

    public Symbol(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Symbol) {
            Symbol other = (Symbol) obj;
            return value.equals(other.value);
        }

        return false;
    }
}
