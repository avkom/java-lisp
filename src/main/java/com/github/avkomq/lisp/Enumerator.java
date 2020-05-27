package com.github.avkomq.lisp;

import java.util.Iterator;

public class Enumerator<T> {
    Iterator<T> iterator;
    T current;

    public Enumerator(Iterable<T> iterable) {
        iterator = iterable.iterator();
    }

    public T getCurrent() {
        return current;
    }

    public boolean moveNext() {
        if (iterator.hasNext()) {
            current = iterator.next();
            return true;
        }
        return false;
    }
}
