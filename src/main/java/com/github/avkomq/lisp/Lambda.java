package com.github.avkomq.lisp;

@FunctionalInterface
public interface Lambda {
    Object Invoke(Object[] args);
}
