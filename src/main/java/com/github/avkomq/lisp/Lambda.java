package com.github.avkomq.lisp;

@FunctionalInterface
public interface Lambda {
    Object invoke(Object[] args);
}
