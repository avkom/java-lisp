package com.github.avkomq.lisp;

import java.util.Iterator;
import java.util.Map;

public class Printer {
    public String print(Object ast) {
        StringBuilder builder = new StringBuilder();
        printValue(ast, builder);
        return builder.toString();
    }

    private void printValue(Object ast, StringBuilder builder) {
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
        else if (ast instanceof Map) {
            printMap((Map) ast, builder);
        }
        else if (ast instanceof Closure) {
            Closure closure = (Closure) ast;
            builder.append("(lambda ");
            printIterable(closure.getParameters(), builder);
            builder.append(" ");
            printValue(closure.getBody(), builder);
            builder.append(" ");
            printIterable(closure.getEnvironment().getMap().keySet(), builder);
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
            printValue(iterator.next(), builder);

            if (iterator.hasNext()) {
                builder.append(" ");
            }
        }

        builder.append(")");
    }

    private void printMap(Map map, StringBuilder builder) {
        builder.append("(");
        map.forEach((key, value) -> {
            builder.append(":");
            builder.append(key);
            builder.append(" ");
            builder.append(value);
            builder.append(" ");
        });
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
    }
}
