package com.github.avkomq.lisp;

import java.util.Collections;
import java.util.Iterator;

public class Printer {
    public String print(Object ast) {
        StringBuilder builder = new StringBuilder();
        print(ast, builder);
        return builder.toString();
    }

    private void print(Object ast, StringBuilder builder) {
        if (ast == null) {
            builder.append("nil");
        }
        else if (ast == Boolean.TRUE) {
            builder.append("#t");
        }
        else if (ast == Boolean.FALSE) {
            builder.append("#f");
        }
        else if (ast instanceof Double && (double) ast == Double.POSITIVE_INFINITY) {
            builder.append("Inf");
        }
        else if (ast instanceof Double && (double) ast == Double.NEGATIVE_INFINITY) {
            builder.append("-Inf");
        }
        else if (ast instanceof String) {
            builder.append("\"");
            builder.append(ast.toString());
            builder.append("\"");
        }
        else if (ast instanceof Iterable) {
            printIterable((Iterable<Object>) ast, builder);
        }
        else if (ast instanceof Closure) {
            Closure closure = (Closure) ast;
            builder.append("(lambda ");
            printIterable(closure.getParameters(), builder);
            builder.append(" ");
            print(closure.getBody());
            builder.append(" ");
            print(closure.getEnvironment().getMap());
            builder.append(")");
        }
        else {
            builder.append(ast.toString());
        }
    }

    private void printIterable(Iterable iterable, StringBuilder builder) {
        Iterator iterator = iterable.iterator();
        builder.append("(");

        while (iterator.hasNext()) {
            print(iterator.next(), builder);

            if (iterator.hasNext()) {
                builder.append(" ");
            }
        }

        builder.append(")");
    }
}
